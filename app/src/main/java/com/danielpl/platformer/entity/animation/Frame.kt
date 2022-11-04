package com.danielpl.platformer.entity.animation

import com.danielpl.platformer.engine

class Frame(
    val sprite: String,
    width: Float,
    height: Float,
    val gameCyclesDuration: Int
) {
    var bitmap = engine.pool.createBitmap(sprite, width, height)

}