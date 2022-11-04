package com.danielpl.platformer.entity.dynamicEnt


class Collectible(sprite: String, x: Float, y: Float) : BoundingEntity(sprite, x, y) {
    var collected = false
}