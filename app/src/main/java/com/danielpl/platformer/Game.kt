package com.danielpl.platformer

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.danielpl.platformer.entity.Entity
import com.danielpl.platformer.entity.Viewport
import com.danielpl.platformer.preferences.Preferences
import com.danielpl.platformer.repository.HighScoreRepository
import com.danielpl.platformer.util.Config.METERS_TO_SHOW_X
import com.danielpl.platformer.util.Config.METERS_TO_SHOW_Y
import com.danielpl.platformer.util.Config.NANOS_TO_SECOND
import com.danielpl.platformer.util.Config.PIXELS_PER_METER
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

lateinit var engine: Game

@AndroidEntryPoint
class Game(gameActivityContext: Context) : SurfaceView(gameActivityContext), Runnable,
    SurfaceHolder.Callback {
    private val stageWidth = getScreenWidth()/2
    private val stageHeight = getScreenHeight()/2
    private val visibleEntities = ArrayList<Entity>()

    init {
        engine = this
        holder.addCallback(this)
        holder.setFixedSize(stageWidth, stageHeight)
    }

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var repository: HighScoreRepository

    private lateinit var gameThread: Thread

    @Volatile
    private var isRunning = false
    private var isGameOver = false

    private var levelManager = LevelManager(TestLevel())

    private val paint = Paint()
    val camera = Viewport(stageWidth, stageHeight, METERS_TO_SHOW_X,METERS_TO_SHOW_Y)
    val transform = Matrix()
    val position = PointF()

    fun worldHeight() = levelManager.levelHeight
    fun worldToScreenX(worldDistance: Float) = (worldDistance * PIXELS_PER_METER)
    fun worldToScreenY(worldDistance: Float) = (worldDistance * PIXELS_PER_METER)
    fun screenToWorldX(pixelDistance: Float) = (pixelDistance/PIXELS_PER_METER)
    fun screenToWorldY(pixelDistance: Float) = (pixelDistance/PIXELS_PER_METER)

    fun getScreenHeight() = context.resources.displayMetrics.heightPixels
    fun getScreenWidth() = context.resources.displayMetrics.widthPixels


    // Game loop
    override fun run() {
        var lastFrame = System.nanoTime()
        while (isRunning) {
            val deltaTime = (System.nanoTime() - lastFrame) * NANOS_TO_SECOND
            update(deltaTime)
            // give entities access to controllers
            buildVisibleSet()
            render(visibleEntities)
        }
    }

    private fun buildVisibleSet(){
        visibleEntities.clear()
        for(e in levelManager.entities){
            if(camera.inView(e)){
                visibleEntities.add(e)
            }
        }
    }

    private fun update(deltaTime: Float) {
        levelManager.update(deltaTime)
    }

    private fun render(visibleSet: ArrayList<Entity>) {
        val canvas = acquireAndLockCanvas() ?: return
        canvas.drawColor(Color.BLUE)
        for(e in visibleSet){
            transform.reset()
            camera.worldToScreen(e,position)
            transform.postTranslate(position.x, position.y)
            e.render(canvas, transform, paint)
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
        isRunning = false
        try {
            gameThread.join()
        } catch (e: Exception) {
            Log.d(R.string.game_tag.toString(), "Error while pausing: $e")
        }

    }

    fun resume() {
        Log.d(R.string.game_tag.toString(), "resume")
        isRunning = true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d(R.string.game_tag.toString(), "surfaceCreated")
        gameThread = Thread(this)
        gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(R.string.game_tag.toString(), "surfaceChanged, width: $width, height: $height")
        Log.d(R.string.game_tag.toString(), "screen width: ${getScreenWidth()}, height: ${getScreenHeight()}")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(R.string.game_tag.toString(), "surfaceDestroyed")
    }

}