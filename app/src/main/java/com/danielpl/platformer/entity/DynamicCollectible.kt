package com.danielpl.platformer.entity

import com.danielpl.platformer.util.Config
import com.danielpl.platformer.util.Config.collectedCollectibles
import com.danielpl.platformer.util.Jukebox
import com.danielpl.platformer.util.SFX

class DynamicCollectible(sprite: String, x: Float, y: Float): DynamicEntity(sprite, x, y) {


    override fun onCollision(that: Entity, jukebox: Jukebox) {
        if(that is Player){
            jukebox.play(SFX.coin)
            collectedCollectibles++
            super.onCollision(that, jukebox)
        }
        else{
            super.onCollision(that, jukebox)
        }
    }
}