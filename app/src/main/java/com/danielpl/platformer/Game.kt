package com.danielpl.platformer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.danielpl.platformer.preferences.Preferences
import com.danielpl.platformer.repository.HighScoreRepository
import com.danielpl.platformer.util.Config
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

lateinit var engine: Game
@AndroidEntryPoint
class Game(gameActivityContext: Context) : SurfaceView(gameActivityContext), Runnable,
    SurfaceHolder.Callback {

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

    init {
        engine = this
        holder.addCallback(this)
        holder.setFixedSize(Config.STAGE_WIDTH, Config.STAGE_HEIGHT)
    }
    val pixelsPerMeter = 50
    fun worldToScreenX(worldDistance: Float) = (worldDistance * pixelsPerMeter).toInt()
    fun worldToScreenY(worldDistance: Float) = (worldDistance * pixelsPerMeter).toInt()
    fun screenToWorldX(pixelDistance: Float) = (pixelDistance/pixelsPerMeter)
    fun screenToWorldY(pixelDistance: Float) = (pixelDistance/pixelsPerMeter)


    // Game loop
    override fun run() {
        while (isRunning) {
            // We cannot reuse a Thread object
            update()
            render()
        }
    }

    private fun update() {
        // levelManager.update()
    }

    private fun render() {
        val canvas = acquireAndLockCanvas() ?: return
        canvas.drawColor(Color.BLACK)
        for(e in levelManager.entities){
            e.render(canvas, paint)
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
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(R.string.game_tag.toString(), "surfaceDestroyed")
    }

}