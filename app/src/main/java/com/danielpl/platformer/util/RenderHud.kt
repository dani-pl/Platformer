package com.danielpl.platformer.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.*
import android.graphics.Paint
import com.danielpl.platformer.R
import com.danielpl.platformer.engine
import com.danielpl.platformer.util.Config.COIN
import com.danielpl.platformer.util.Config.EMPTY_HEART
import com.danielpl.platformer.util.Config.FULL_HEART
import com.danielpl.platformer.util.Config.MARGIN
import com.danielpl.platformer.util.Config.TEXT_SIZE
import com.danielpl.platformer.util.Config.heart_size
import com.danielpl.platformer.util.Config.isGameOver
import com.danielpl.platformer.util.Config.isLevelSuccessful
import kotlin.math.abs

class RenderHud(
    private val canvas: Canvas,
    private val paint: Paint,
    private val context: Context
) {
    private val textSize = TEXT_SIZE
    private val margin = MARGIN
    private val fullHeartBitmap = engine.pool.createBitmap(FULL_HEART, heart_size, heart_size)
    private val bitmapHeartSize = fullHeartBitmap.height.toFloat()
    private val emptyHeartBitmap = engine.pool.createBitmap(EMPTY_HEART, heart_size, heart_size)
    private val coinBitmap = engine.pool.createBitmap(COIN, heart_size, heart_size)

    init {
        paint.color = WHITE
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = textSize
    }

    fun showHealth(playerHealth: Int, paint: Paint) {
        var position = 0
        val lostLives = abs(playerHealth - 3)
        for (i in 0..playerHealth) {
            if (i == 0) {
                continue
            }
            canvas.drawBitmap(
                fullHeartBitmap,
                margin + (bitmapHeartSize * position),
                margin,
                paint
            )
            position++
        }
        for (i in 0..lostLives) {
            if (i == 0) {
                continue
            }
            canvas.drawBitmap(
                emptyHeartBitmap,
                margin + (bitmapHeartSize * position),
                margin,
                paint
            )
            position++
        }
    }

    fun showCollectibles(NumberOfCollectibles: Int, paint: Paint, totalCollectibles: Int) {
        canvas.drawBitmap(
            coinBitmap,
            margin + (bitmapHeartSize * 4),
            margin,
            paint
        )
        canvas.drawText(
            context.getString(
                R.string.NumberCoins,
                NumberOfCollectibles.toString(),
                totalCollectibles.toString()
            ), 2 * margin + (bitmapHeartSize * 5),
            TEXT_SIZE, paint
        )
    }

    fun gameOverOrLevelSuccessful() {
        paint.textAlign = Paint.Align.CENTER
        paint.color = BLACK
        paint.isFakeBoldText = true
        val centerX = Config.STAGE_WIDTH * 0.5f
        val centerY = Config.STAGE_HEIGHT * 0.5f
        if (isGameOver) {
            paint.color = BLACK
            canvas.drawText(
                context.getString(R.string.game_over),
                centerX,
                centerY - TEXT_SIZE,
                paint
            )
            canvas.drawText(context.getString(R.string.restart), centerX, centerY, paint)
        } else if (isLevelSuccessful) {
            paint.color = GREEN
            canvas.drawText(
                context.getString(R.string.level_successful),
                centerX,
                centerY - TEXT_SIZE,
                paint
            )
            canvas.drawText(context.getString(R.string.next_level), centerX, centerY, paint)
        }
    }
}