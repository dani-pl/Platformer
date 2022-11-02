package com.danielpl.platformer.entity

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import androidx.core.math.MathUtils
import com.danielpl.platformer.engine
import com.danielpl.platformer.entity.dynamicEnt.DynamicEntity
import com.danielpl.platformer.gamepad.InputManager
import com.danielpl.platformer.util.Config
import com.danielpl.platformer.util.Config.LEFT
import com.danielpl.platformer.util.Config.PLAYER_JUMP_FORCE
import com.danielpl.platformer.util.Config.PLAYER_RUN_SPEED
import com.danielpl.platformer.util.Config.RIGHT


class Player(sprite1: String, sprite2: String, sprite3: String, posX: Float, posY: Float) :
    DynamicEntity(sprite1, posX, posY) {
    private var facing = LEFT
    var blinking = false
    private var blinkingDt = 0
    private var nextSpriteNumber = 1
    private var bitmapSprite1: Bitmap = engine.pool.createBitmap(sprite1, width, height)
    private var bitmapSprite2: Bitmap = engine.pool.createBitmap(sprite2, width, height)
    private var bitmapSprite3: Bitmap = engine.pool.createBitmap(sprite3, width, height)

    override fun render(canvas: Canvas, transform: Matrix, paint: Paint) {
        if (isVisible()) {
            transform.preScale(facing, 1.0f)
            if (facing == RIGHT) {
                val offset = engine.worldToScreenX(width)
                transform.postTranslate(offset, 0.0f)
            }
            val controls: InputManager = engine.getControls()
            val direction: Float = controls.horizontalFactor
            if(direction !=0f){
                when (nextSpriteNumber) {
                    1 -> {
                        bitmap = bitmapSprite1
                        nextSpriteNumber = 2
                    }
                    2 -> {
                        bitmap = bitmapSprite2
                        nextSpriteNumber = 3
                    }
                    3 -> {
                        bitmap = bitmapSprite3
                        nextSpriteNumber = 1
                    }
                }
            }

            canvas.drawBitmap(bitmap, transform, paint)
            //super.render(canvas, transform, paint)
        }
    }


    override fun update(dt: Float) {
        val controls: InputManager = engine.getControls()
        val direction: Float = controls.horizontalFactor
        velX = direction * PLAYER_RUN_SPEED
        x += MathUtils.clamp(velX, -Config.MAX_DELTA, Config.MAX_DELTA)
        facing = getFacingDirection(direction)
        if (controls.isJumping && isOnGround) {
            velY = PLAYER_JUMP_FORCE
            isOnGround = false
        }
        if (blinking) {
            blinkingDt++
        }
        super.update(dt)
    }

    private fun isVisible(): Boolean {
        if (blinking) {
            when {
                blinkingDt > 200 -> {
                    blinking = false
                    blinkingDt = 0
                    return true
                }
                blinkingDt % 20 < 10 -> {
                    return false
                }
                blinkingDt % 20 > 10 -> {
                    return true
                }
            }
        }
        return true
    }

    private fun getFacingDirection(direction: Float): Float {
        if (direction < 0.0f) {
            return LEFT
        } else if (direction > 0.0f) {
            return RIGHT
        }
        return facing
    }
}