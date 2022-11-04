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
        // when the entity is on the air, it keeps falling at a fixed rate
        if (!isOnGround) {
            velY += GRAVITY / 17
        }
        y += clamp(velY, -MAX_DELTA, MAX_DELTA)
        if (top() > engine.worldHeight()) {
            setBottom(0f)
        }
        // the entity is on the air
        isOnGround = false
    }

    override fun onCollision(that: Entity, jukebox: Jukebox) {
        getOverlap(this, that, overlap)
        x += overlap.x
        y += overlap.y
        // When the entity touches something with its bottom part. it stops moving
        if (overlap.y != 0f) {
            velY = 0.0f
            // the entity is on the floor
            isOnGround = true
        }
    }
}