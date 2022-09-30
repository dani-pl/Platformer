package com.danielpl.platformer

import com.danielpl.platformer.entity.DynamicEntity
import com.danielpl.platformer.entity.Entity
import com.danielpl.platformer.entity.StaticEntity
import com.danielpl.platformer.entity.isColliding
import com.danielpl.platformer.util.Config.NO_TILE
import com.danielpl.platformer.util.Config.PLAYER

class LevelManager(level: LevelData) {
    var levelHeight = 0
    lateinit var player: DynamicEntity
    val entities = ArrayList<Entity>()
    val entitiesToAdd = ArrayList<Entity>()
    val entitiesToRemove = ArrayList<Entity>()

    init {
        loadAssets(level)
    }

    fun update(dt: Float) {
        for (e in entities) {
            e.update(dt)
        }
        doCollisionsChecks()

        addAndRemoveEntities()
    }

    private fun doCollisionsChecks() {
        for(e in entities){
            if(isColliding(player,e)){
                player.onCollision(e)
                e.onCollision(player)
            }
        }
    }

    private fun loadAssets(level: LevelData) {
        levelHeight = level.getLevelHeight()
        for (y in 0 until levelHeight) {
            val row = level.getRow(y)
            for (x in row.indices) {
                val tileID = row[x]
                if (tileID == NO_TILE) continue
                val spriteName = level.getSpriteName(tileID)
                createEntity(spriteName, x, y)
            }
        }
        addAndRemoveEntities()
    }

    private fun createEntity(spriteName: String, x: Int, y: Int) {
        if (spriteName.equals(PLAYER, ignoreCase = true)) {
            player = DynamicEntity(spriteName, x.toFloat(), y.toFloat())
            addEntity(player)
        } else {
            addEntity(StaticEntity(spriteName, x.toFloat(), y.toFloat()))
        }
    }

    fun addEntity(e: Entity) {
        entitiesToAdd.add(e)
    }

    fun removeEntity(e: Entity) {
        entitiesToRemove.add(e)
    }

    private fun addAndRemoveEntities() {
        for (e in entitiesToRemove) {
            entities.remove(e)
        }
        for (e in entitiesToAdd) {
            entities.add(e)
        }
        entitiesToAdd.clear()
        entitiesToRemove.clear()
    }

    private fun cleanup() {
        addAndRemoveEntities()
        for (e in entities) {
            e.destroy()
        }
        entities.clear()
    }

    fun destroy() {
        cleanup()
    }
}