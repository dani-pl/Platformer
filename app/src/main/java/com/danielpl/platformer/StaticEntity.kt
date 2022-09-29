package com.danielpl.platformer

import android.graphics.Bitmap
import com.danielpl.platformer.entity.Entity
import com.danielpl.platformer.util.BitmapUtils

class StaticEntity(sprite: String, x: Float, y: Float) : Entity() {
    lateinit var bitmap: Bitmap

    init {
        this.x = x
        this.y = y
        width = 1.0f
        height = 1.0f
        val widthInPixels = engine.worldToScreenX(width)
        val heightInPixels = engine.worldToScreenX(height)
        bitmap = BitmapUtils.loadScaledBitmap(engine.context, sprite, widthInPixels, heightInPixels)
    }
}