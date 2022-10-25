package com.danielpl.platformer.entity

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import androidx.core.math.MathUtils
import com.danielpl.platformer.engine
import com.danielpl.platformer.gamepad.InputManager
import com.danielpl.platformer.util.Config
import com.danielpl.platformer.util.Config.GRAVITY

const val PLAYER_RUN_SPEED = 6.0f //meters per second
val PLAYER_JUMP_FORCE: Float = -(GRAVITY) //whatever feels good!
val LEFT = 1.0f
val RIGHT = -1.0f

class Player(spriteName: String, xpos: Float, ypos: Float) :
    DynamicEntity(spriteName, xpos, ypos) {
    val TAG = "Player"
    var facing = LEFT
    var blinking = false
    var blinking_dt = 0

    override fun render(canvas: Canvas, transform: Matrix, paint: Paint) {
        if (isVisible()) {
            transform.preScale(facing, 1.0f)
            if (facing == RIGHT) {
                val offset = engine.worldToScreenX(width)
                transform.postTranslate(offset, 0.0f)
            }
            super.render(canvas, transform, paint)
        }
    }

    override fun update(dt: Float) {
        val controls: InputManager = engine.getControls()
        val direction: Float = controls._horizontalFactor
        velX = direction * PLAYER_RUN_SPEED
        x += MathUtils.clamp(velX, -Config.MAX_DELTA, Config.MAX_DELTA)
        facing = getFacingDirection(direction)
        if (controls._isJumping && isOnGround) {
            velY = PLAYER_JUMP_FORCE
            isOnGround = false
        }
        if (blinking) {
            blinking_dt++
        }
        super.update(dt)
    }

    private fun isVisible(): Boolean {
        if (blinking) {
            when {
                blinking_dt >200 -> {
                    blinking = false
                    blinking_dt = 0
                    return true
                }
                blinking_dt % 20 < 10 -> {
                    return false
                }
                blinking_dt % 20 > 10 -> {
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