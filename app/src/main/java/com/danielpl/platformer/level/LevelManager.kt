package com.danielpl.platformer.level

import com.danielpl.platformer.entity.*
import com.danielpl.platformer.util.Config.BLOCK
import com.danielpl.platformer.util.Config.DYNAMIC_COLLECTIBLE
import com.danielpl.platformer.util.Config.NO_TILE
import com.danielpl.platformer.util.Config.PLAYER_BLUE
import com.danielpl.platformer.util.Config.PLAYER_BROWN
import com.danielpl.platformer.util.Config.SPEAR
import com.danielpl.platformer.util.Jukebox

class LevelManager(level: LevelData) {
    var levelHeight = 0
    lateinit var player: Player
    val entities = ArrayList<Entity>()
    val entitiesToAdd = ArrayList<Entity>()
    val entitiesToRemove = ArrayList<Entity>()
    var collectibles = ArrayList<DynamicCollectible>()

    init {
        loadAssets(level)
    }

    fun update(dt: Float, jukebox: Jukebox) {
        for (e in entities) {
            e.update(dt)
        }
        doCollisionsChecks(jukebox)

        addAndRemoveEntities()
    }

    private fun doCollisionsChecks(jukebox: Jukebox) {
        for(e in entities){
            if(e == player) continue
            if(isColliding(player,e)) {
                e.onCollision(player, jukebox)
                if(e is DynamicCollectible){
                    removeEntity(e)
                    continue
                }
                player.onCollision(e, jukebox)
            } else if(e !is DynamicCollectible || e !is DynamicEnemy){
                if(e !is DynamicCollectible) {
                    entities.filter {
                        (it is DynamicCollectible)
                    }.forEach {
                        if (isColliding(e, it)) {
                            it.onCollision(e, jukebox)
                        }
                    }
                }
                if(e !is DynamicEnemy) {
                    entities.filter {
                        (it is DynamicEnemy)
                    }.forEach {
                        if (isColliding(e, it)) {
                            it.onCollision(e, jukebox)
                        }
                    }
                }

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
        when(spriteName) {
            PLAYER_BLUE -> {
                player = Player(spriteName, x.toFloat(), y.toFloat())
                addEntity(player)
            }
            PLAYER_BROWN->{
                player = Player(spriteName, x.toFloat(), y.toFloat())
                addEntity(player)
            }
            SPEAR-> {
                addEntity(StaticEnemy(spriteName, x.toFloat(), y.toFloat(), Enemies.SPEARS))
            }
            BLOCK -> {
                addEntity(DynamicEnemy(spriteName, x.toFloat(), y.toFloat()))
            }
            DYNAMIC_COLLECTIBLE ->{
                val collectible = DynamicCollectible(spriteName, x.toFloat(), y.toFloat())
                addEntity(collectible)
                collectibles.add(collectible)
            }
            else -> {
                addEntity(StaticEntity(spriteName, x.toFloat(), y.toFloat()))
            }
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