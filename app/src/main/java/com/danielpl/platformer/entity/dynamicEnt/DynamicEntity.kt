package com.danielpl.platformer.entity.dynamicEnt

import androidx.core.math.MathUtils.clamp
import com.danielpl.platformer.engine
import com.danielpl.platformer.entity.*
import com.danielpl.platformer.entity.staticEnt.StaticEntity
import com.danielpl.platformer.util.Config.GRAVITY
import com.danielpl.platformer.util.Config.MAX_DELTA
import com.danielpl.platformer.util.Jukebox


open class DynamicEntity(sprite: String, x: Float, y: Float) : StaticEntity(sprite, x, y) {
    var velX = 0f
    var velY = 0f
    var isOnGround = false

    override fun update(dt: Float) {
        if (!isOnGround) {
            velY += GRAVITY / 17
        }
        y += clamp(velY, -MAX_DELTA, MAX_DELTA)
        if (top() > engine.worldHeight()) {
            setBottom(0f)
        }
        isOnGround = false
    }

    override fun onCollision(that: Entity, jukebox: Jukebox) {
        getOverlap(this, that, overlap)
        x += overlap.x
        y += overlap.y
        if (overlap.y != 0f) {
            if (this is DynamicCollectible) {
                velY = 0.0f
            }
            if (this is DynamicEnemy) {
                velY = 0.0f
            }
            if (this is Player && that is DynamicEnemy) {
                velY = 0.0f
            }
            if (overlap.y < 0f && that !is Player) { //we've hit our feet
                if ((this is DynamicCollectible || this is DynamicEnemy)) {
                    velY -= GRAVITY * 0.8.toFloat()
                } else {
                    isOnGround = true
                }
            } // overlap.y > 0f == we've hit our head
        }
    }
}