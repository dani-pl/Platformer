package com.danielpl.platformer.entity.dynamicEnt

import com.danielpl.platformer.entity.Entity
import com.danielpl.platformer.entity.getOverlap
import com.danielpl.platformer.entity.overlap
import com.danielpl.platformer.util.Config
import com.danielpl.platformer.util.Jukebox

open class BoundingEntity(sprite: String, x: Float, y: Float) : DynamicEntity(sprite, x, y) {

    override fun onCollision(that: Entity, jukebox: Jukebox) {
        getOverlap(this, that, overlap)
        x += overlap.x
        y += overlap.y
        // If the entity touches another entity with its bottom part, it bounds at a fixed rate
        if (overlap.y < 0f) {
            velY -= Config.GRAVITY * 0.8f
            isOnGround = true
        }
        // if the entity touches another entity with its upper part, then it is the responsibility
        // of the entity which is jumping over to make sure they do not pass through each other

        // there is not call to the super class because the Dynamic Entity stops moving
        // when it touches the floor

    }
}