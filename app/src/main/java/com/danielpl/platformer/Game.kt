package com.danielpl.platformer

import android.content.Context
import android.graphics.*
import android.graphics.Color.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.danielpl.platformer.entity.Entity
import com.danielpl.platformer.entity.Viewport
import com.danielpl.platformer.gamepad.InputManager
import com.danielpl.platformer.level.LevelManager
import com.danielpl.platformer.level.Level
import com.danielpl.platformer.util.*
import com.danielpl.platformer.util.Config.METERS_TO_SHOW_X
import com.danielpl.platformer.util.Config.METERS_TO_SHOW_Y
import com.danielpl.platformer.util.Config.NANOS_TO_SECOND
import com.danielpl.platformer.util.Config.PIXELS_PER_METER
import com.danielpl.platformer.util.Config.collectedCollectibles
import com.danielpl.platformer.util.Config.isGameOver
import com.danielpl.platformer.util.Config.isLevelSuccessful
import com.danielpl.platformer.util.Config.playerHealth
import com.danielpl.platformer.util.Config.restart
import com.danielpl.platformer.util.Config.totalCollectibles
import com.danielpl.platformer.util.Config.level

lateinit var engine: Game

class Game(context: Context, attrs: AttributeSet? = null) : SurfaceView(context, attrs), Runnable,
    SurfaceHolder.Callback {
    private val stageWidth = getScreenWidth() / 2
    private val stageHeight = getScreenHeight() / 2
    private val visibleEntities = ArrayList<Entity>()

    private val assetManager = context.assets


    private var jukebox = Jukebox(context)

    init {
        engine = this
        holder.addCallback(this)
        holder.setFixedSize(stageWidth, stageHeight)
    }

    private var inputs = InputManager() // a valid null-controller


    private lateinit var gameThread: Thread

    @Volatile
    private var isRunning = false

    private val camera = Viewport(stageWidth, stageHeight, METERS_TO_SHOW_X, METERS_TO_SHOW_Y)
    val pool = BitmapPool(this)
    private var levelManager = LevelManager(Level("v1_lv$level.txt", assetManager))

    private val paint = Paint()
    private val transform = Matrix()
    private val position = PointF()

    fun worldHeight() = levelManager.levelHeight
    fun worldToScreenX(worldDistance: Float) = (worldDistance * PIXELS_PER_METER)
    fun worldToScreenY(worldDistance: Float) = (worldDistance * PIXELS_PER_METER)
    /*

    //Functions are not used
    fun screenToWorldX(pixelDistance: Float) = (pixelDistance/PIXELS_PER_METER)
    fun screenToWorldY(pixelDistance: Float) = (pixelDistance/PIXELS_PER_METER)

     */

    private fun getScreenHeight() = context.resources.displayMetrics.heightPixels
    private fun getScreenWidth() = context.resources.displayMetrics.widthPixels

    fun setControls(input: InputManager) {
        inputs.onPause()
        inputs.onStop()
        inputs = input
        inputs.onResume()
        inputs.onStart()
    }

    fun getControls() = inputs

    private fun restart() {
        restart = false
        if (isLevelSuccessful) {
            if (level == 1) {
                levelManager = LevelManager(Level("v1_lv2.txt", assetManager))
                level = 2
            } else {
                levelManager = LevelManager(Level("v1_lv1.txt", assetManager))
                level = 1
            }
            jukebox.unloadMusic()
            jukebox.loadMusic()

        } else {
            levelManager = LevelManager(Level("v1_lv$level.txt", assetManager))
        }
        jukebox.resumeBgMusic()
        isGameOver = false
        isLevelSuccessful = false
        playerHealth = 3
        collectedCollectibles = 0
    }


    // Game loop
    override fun run() {
        val lastFrame = System.nanoTime()
        while (isRunning) {
            if (restart) {
                restart()
            }
            val deltaTime = (System.nanoTime() - lastFrame) * NANOS_TO_SECOND
            update(deltaTime)
            buildVisibleSet()
            render(visibleEntities)

        }
    }


    private fun buildVisibleSet() {
        visibleEntities.clear()
        for (e in levelManager.entities) {
            if (camera.inView(e)) {
                visibleEntities.add(e)
            }
        }
    }

    private fun update(deltaTime: Float) {
        levelManager.update(deltaTime, jukebox)
        camera.lookAt(levelManager.player)
        checkGameOverOrLevelSuccessful()
    }

    private fun checkGameOverOrLevelSuccessful() {
        if (playerHealth < 1) {
            if (!isGameOver) {
                jukebox.pauseBgMusic()
                jukebox.play(SFX.death)

                isGameOver = true
            }
        }

        if (collectedCollectibles == totalCollectibles) {
            if (!isLevelSuccessful) {
                jukebox.pauseBgMusic()
                jukebox.play(SFX.win)

                isLevelSuccessful = true
            }
        }
    }

    private fun render(visibleSet: ArrayList<Entity>) {
        val canvas = acquireAndLockCanvas() ?: return
        if (level == 1) {
            canvas.drawColor(BLUE)
        } else {
            canvas.drawColor(YELLOW)
        }
        for (e in visibleSet) {
            transform.reset()
            camera.worldToScreen(e, position)
            transform.postTranslate(position.x, position.y)
            e.render(canvas, transform, paint)
        }

        val renderHud = RenderHud(canvas, paint, context)
        if (!isGameOver && !isLevelSuccessful) {
            renderHud.showHealth(playerHealth, paint)
            renderHud.showCollectibles(collectedCollectibles, paint, totalCollectibles)
        } else {
            renderHud.gameOverOrLevelSuccessful()
        }
        holder.unlockCanvasAndPost(canvas)
    }


    private fun acquireAndLockCanvas(): Canvas? {
        if (holder?.surface?.isValid == false) {
            return null
        }
        return holder.lockCanvas()
    }


    fun pause() {
        inputs.onPause()
        isRunning = false
        jukebox.pauseBgMusic()
        try {
            gameThread.join()
        } catch (e: Exception) {
            Log.d(R.string.game_tag.toString(), "Error while pausing: $e")
        }

    }

    fun resume() {
        Log.d(R.string.game_tag.toString(), "resume")
        inputs.onResume()
        isRunning = true
        jukebox.play(SFX.starting)
        jukebox.resumeBgMusic()
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d(R.string.game_tag.toString(), "surfaceCreated")
        gameThread = Thread(this)
        gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(R.string.game_tag.toString(), "surfaceChanged, width: $width, height: $height")
        Log.d(
            R.string.game_tag.toString(),
            "screen width: ${getScreenWidth()}, height: ${getScreenHeight()}"
        )
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(R.string.game_tag.toString(), "surfaceDestroyed")
    }

}