package com.danielpl.platformer.util


object Config {
    const val STAGE_WIDTH = 1080
    const val STAGE_HEIGHT = 720
    const val GRAVITY = 0.5f
    const val DEFAULT_MUSIC_VOLUME = 0.6f
    const val TEXT_SIZE = 55f
    const val MARGIN = 10f
    const val PLAYER_BLUE = "lightblue_left1"
    const val PLAYER_BROWN = "brown_left1"
    const val NULLSPRITE = "nullsprite"
    const val SPEAR = "spearsdown_brown"
    const val BLOCK = "enemyblockbrown"
    const val NO_TILE = 0
    const val PIXELS_PER_METER = 50
    const val METERS_TO_SHOW_X = 20f
    const val METERS_TO_SHOW_Y = 0f
    const val NANOS_TO_SECOND = 1.0f / 1000000000f
    const val MAX_DELTA = 0.48f
    const val FULL_HEART = "lifehearth_full"
    const val EMPTY_HEART = "lifehearth_empty"
    const val COIN = "coinyellow"
    const val heart_size = 1.0f

    const val PLAYER_RUN_SPEED = 6.0f //meters per second
    const val PLAYER_JUMP_FORCE: Float = -(GRAVITY) //whatever feels good!
    const val LEFT = 1.0f
    const val RIGHT = -1.0f

    const val PLAYER_WIDTH = 1f
    const val PLAYER_HEIGHT = 1f
    const val PLAYER_FRAME_LIFECYCLES_DURATION = 3
    val PLAYER_SPRITES_LEVEL1 = listOf("lightblue_left1", "lightblue_left2", "lightblue_left3")
    val PLAYER_SPRITES_LEVEL2 = listOf("brown_left1", "brown_left2", "brown_left3")


    @Volatile
    var isGameOver = false


    @Volatile
    var isLevelSuccessful = false

    @Volatile
    var restart = false

    @Volatile
    var totalCollectibles = 0


    var level = 1
}