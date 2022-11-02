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

    @Volatile
    var isGameOver = false


    @Volatile
    var isLevelSuccessful = false


    @Volatile
    var restart = false

    @Volatile
    var playerHealth = 3

    @Volatile
    var collectedCollectibles = 0

    @Volatile
    var totalCollectibles = 0


    var level = 1
}