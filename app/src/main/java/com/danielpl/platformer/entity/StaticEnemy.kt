package com.danielpl.platformer.entity

import com.danielpl.platformer.util.Config.playerHealth
import com.danielpl.platformer.util.Jukebox
import com.danielpl.platformer.util.SFX
import javax.inject.Inject


open class StaticEnemy(sprite: String, x: Float, y: Float, private val type: Enemies) :
    StaticEntity(sprite, x, y) {


    override fun onCollision(that: Entity, jukebox: Jukebox) {
        getOverlap(that, this, overlap)
        val player = (that as Player)
        if (overlap.y < 0f && !player.blinking ) {
            player.blinking = true
            jukebox.play(SFX.spike)

            playerHealth--
        }
    }
}