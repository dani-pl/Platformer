package com.danielpl.platformer.entity.dynamicEnt

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import androidx.core.math.MathUtils
import com.danielpl.platformer.engine
import com.danielpl.platformer.entity.Entity
import com.danielpl.platformer.entity.overlap
import com.danielpl.platformer.gamepad.InputManager
import com.danielpl.platformer.entity.animation.Animation
import com.danielpl.platformer.entity.staticEnt.StaticEnemy
import com.danielpl.platformer.util.Config
import com.danielpl.platformer.util.Config.LEFT
import com.danielpl.platformer.util.Config.PLAYER_JUMP_FORCE
import com.danielpl.platformer.util.Config.PLAYER_RUN_SPEED
import com.danielpl.platformer.util.Config.RIGHT
import com.danielpl.platformer.util.Jukebox
import com.danielpl.platformer.util.SFX


class Player(private val animation: Animation, posX: Float, posY: Float) :
    DynamicEntity(animation.frames[0].sprite, posX, posY) {

    private var facing = LEFT
    private var blinking = false
    private var blinkingDt = 0
    var health = 3
    var wallet = 0



    override fun onCollision(that: Entity, jukebox: Jukebox) {
        super.onCollision(that, jukebox)

        // If the collectible is overlapping with the player in some axis, the collectible is collected
        if (that is Collectible) {
            jukebox.play(SFX.coin)
            wallet++
            that.collected = true
        }
        // if a dynamicEnemy is colliding with the player
        if (that is DynamicEnemy) {
            // jumping over it and not blinking
            if (overlap.y > 0f && !blinking) {
                // then, the player loses a life
                blinking = true
                jukebox.play(SFX.block)
                health--
            }
        }
        // If a player touches a static enemy
        if (that is StaticEnemy) {
            // jumping over it and not blinking
            if (overlap.y < 0f && !blinking) {
                // then, the player loses a life
                blinking = true
                jukebox.play(SFX.block)
                health--
            }
        }
    }

    override fun render(canvas: Canvas, transform: Matrix, paint: Paint) {
        if(isVisible()) {
            transform.preScale(facing, 1.0f)
            if (facing == RIGHT) {
                val offset = engine.worldToScreenX(width)
                transform.postTranslate(offset, 0.0f)
            }

            canvas.drawBitmap(animation.getBitmapToRender(), transform, paint)
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
        // If the player is moving keeps the animation running
        if(direction!=0f) {
            animation.update()
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