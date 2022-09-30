package com.danielpl.platformer.util

import android.os.SystemClock.uptimeMillis
import kotlin.random.Random


object Config {
    const val STAGE_WIDTH = 1080
    const val STAGE_HEIGHT = 720
    const val STAR_COUNT = 40
    const val ENEMY_COUNT = 10
    const val PLAYER_HEIGHT = 75
    const val ACCELERATION = 1.1f
    const val MIN_VEL = 0.1f
    const val MAX_VEL = 20f
    const val GRAVITY = 40f
    const val LIFT = -(GRAVITY * 2f)
    const val DRAG = 0.97f
    const val PLAYER_STARTING_HEALTH = 3
    const val PLAYER_STARTING_POSITION = 10f
    const val ENEMY_HEIGHT = 60
    const val ENEMY_SPAWN_OFFSET = STAGE_WIDTH * 2
    const val BLINKING_PERIOD = 3000
    const val BLINKING_ACTIVE = 150
    const val BLINKING_INACTIVE = 100
    const val DEFAULT_MUSIC_VOLUME = 0.6f
    const val DIFFERENT_ENEMIES = 6
    const val DIFFERENT_SHAPES = 2
    const val HEIGHT_MODIFIER_MIN = 0.2
    const val HEIGHT_MODIFIER_MAX = 0.8
    const val VELX_MODIFIER_MIN = 0.5
    const val VELX_MODIFIER_MAX = 3.0
    const val SIN_MODIFIER = 250
    const val OVAL_RANDOMIZER_MIN = 0.1f
    const val RAD_MIN = 10
    const val TEXT_SIZE = 48f
    const val MARGIN = 10f
    const val PLAYER = "lightblue_left1"
    const val NULLSPRITE = "nullsprite"
    const val NO_TILE = 0
    const val PIXELS_PER_METER = 50
    const val METERS_TO_SHOW_X = 20f
    const val METERS_TO_SHOW_Y = 0f
    const val NANOS_TO_SECOND = 1.0f/ 1000000000f
    const val MAX_DELTA = 0.48f


    val RNG = Random(uptimeMillis())

    @Volatile
    var isBoosting = false
    var playerSpeed = 0f
}