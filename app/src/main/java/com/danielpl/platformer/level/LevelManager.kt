package com.danielpl.platformer.level

import com.danielpl.platformer.entity.*
import com.danielpl.platformer.entity.dynamicEnt.DynamicEnemy
import com.danielpl.platformer.entity.dynamicEnt.Player
import com.danielpl.platformer.entity.staticEnt.StaticEnemy
import com.danielpl.platformer.entity.staticEnt.StaticEntity
import com.danielpl.platformer.entity.animation.Animation
import com.danielpl.platformer.util.Config.BLOCK
import com.danielpl.platformer.util.Config.COIN
import com.danielpl.platformer.util.Config.NO_TILE
import com.danielpl.platformer.util.Config.PLAYER_BLUE
import com.danielpl.platformer.util.Config.PLAYER_BROWN
import com.danielpl.platformer.util.Config.PLAYER_FRAME_LIFECYCLES_DURATION
import com.danielpl.platformer.util.Config.PLAYER_HEIGHT
import com.danielpl.platformer.util.Config.PLAYER_SPRITES_LEVEL1
import com.danielpl.platformer.util.Config.PLAYER_SPRITES_LEVEL2
import com.danielpl.platformer.util.Config.PLAYER_WIDTH
import com.danielpl.platformer.util.Config.SPEAR
import com.danielpl.platformer.entity.animation.Frame
import com.danielpl.platformer.entity.dynamicEnt.Collectible
import com.danielpl.platformer.util.Jukebox

class LevelManager(level: LevelData) {
    var levelHeight = 0
    lateinit var player: Player
    val entities = ArrayList<Entity>()
    private val entitiesToAdd = ArrayList<Entity>()
    private val entitiesToRemove = ArrayList<Entity>()
    private var collectibles = ArrayList<Collectible>()
    private var dynamicEnemies = ArrayList<DynamicEnemy>()
    private var playerAnimationLightBlue = Animation(mutableListOf())
    private var playerAnimationBrown = Animation(mutableListOf())

    init {
        createPlayerAnimations()
        loadAssets(level)
    }

    private fun createPlayerAnimations() {

        for (sprite in PLAYER_SPRITES_LEVEL1) {
            playerAnimationLightBlue.frames.add(
                Frame(
                    sprite,
                    PLAYER_WIDTH,
                    PLAYER_HEIGHT,
                    PLAYER_FRAME_LIFECYCLES_DURATION
                )
            )
        }

        for (sprite in PLAYER_SPRITES_LEVEL2) {
            playerAnimationBrown.frames.add(
                Frame(
                    sprite,
                    PLAYER_WIDTH,
                    PLAYER_HEIGHT,
                    PLAYER_FRAME_LIFECYCLES_DURATION
                )
            )
        }

    }

    fun update(dt: Float, jukebox: Jukebox) {
        for (e in entities) {
            e.update(dt)
        }
        doCollisionsChecks(jukebox)

        checkCollectibles()


        addAndRemoveEntities()
    }

    private fun checkCollectibles() {
        for (c in collectibles) {
            if (c.collected) {
                removeEntity(c)
            }
        }
    }

    private fun doCollisionsChecks(jukebox: Jukebox) {
        for (e in entities) {
            if (e == player) {
                continue
            }
            if (isColliding(player, e)) {
                player.onCollision(e, jukebox)
                e.onCollision(player, jukebox)
            }
            for (c in collectibles) {
                if (c == e) {
                    continue
                }
                if (isColliding(c, e)) {
                    e.onCollision(c, jukebox)
                    c.onCollision(e, jukebox)
                }
            }
            for (d in dynamicEnemies) {
                if (d == e) {
                    continue
                }
                if (isColliding(d, e)) {
                    e.onCollision(d, jukebox)
                    d.onCollision(e, jukebox)
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
        when (spriteName) {
            PLAYER_BLUE -> {

                player = Player(playerAnimationLightBlue, x.toFloat(), y.toFloat())
                addEntity(player)
            }
            PLAYER_BROWN -> {
                player = Player(playerAnimationBrown, x.toFloat(), y.toFloat())
                addEntity(player)
            }
            SPEAR -> {
                addEntity(StaticEnemy(spriteName, x.toFloat(), y.toFloat()))
            }
            BLOCK -> {
                val dynamicEnemy = DynamicEnemy(spriteName, x.toFloat(), y.toFloat())
                addEntity(dynamicEnemy)
                dynamicEnemies.add(dynamicEnemy)
            }
            COIN -> {
                val collectible = Collectible(spriteName, x.toFloat(), y.toFloat())
                addEntity(collectible)
                collectibles.add(collectible)
            }
            else -> {
                addEntity(StaticEntity(spriteName, x.toFloat(), y.toFloat()))
            }
        }
    }

    private fun addEntity(e: Entity) {
        entitiesToAdd.add(e)
    }

    private fun removeEntity(e: Entity) {
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

    /*

    // Functions are never used

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

     */
}