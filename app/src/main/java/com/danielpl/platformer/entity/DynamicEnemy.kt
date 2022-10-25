package com.danielpl.platformer.entity

import com.danielpl.platformer.util.Config.playerHealth
import com.danielpl.platformer.util.Jukebox
import com.danielpl.platformer.util.SFX


class DynamicEnemy(sprite: String, x: Float, y: Float) :
    DynamicEntity(sprite, x, y) {


    override fun onCollision(that: Entity, jukebox: Jukebox) {
        getOverlap(that, this, overlap)
        if(that is Player){
            if (overlap.y > 0f && !that.blinking ) {
                that.blinking = true
                jukebox.play(SFX.block)
                playerHealth--
            }
        }
        super.onCollision(that, jukebox)
    }
}