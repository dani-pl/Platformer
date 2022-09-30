package com.danielpl.platformer.entity

import androidx.core.math.MathUtils.clamp
import com.danielpl.platformer.engine
import com.danielpl.platformer.util.Config.GRAVITY
import com.danielpl.platformer.util.Config.MAX_DELTA


class DynamicEntity(sprite: String, x: Float, y: Float) : StaticEntity(sprite, x, y) {
    var velX = 0f
    var velY = 0f
    override fun update(dt: Float) {
        val gravityThisTick = GRAVITY * dt
        velY += gravityThisTick
        x += clamp(velX * dt, -MAX_DELTA, MAX_DELTA)
        y += clamp(velY * dt, -MAX_DELTA, MAX_DELTA)
        if(top()>engine.worldHeight()){
            setBottom(0f)
        }
    }

    override fun onCollision(that: Entity) {
        getOverlap(this, that, overlap)
        x+= overlap.x
        y+= overlap.y
    }
}