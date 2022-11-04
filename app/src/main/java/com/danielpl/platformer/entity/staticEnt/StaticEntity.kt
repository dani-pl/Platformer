package com.danielpl.platformer.entity.staticEnt

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.danielpl.platformer.engine
import com.danielpl.platformer.entity.Entity

open class StaticEntity(sprite: String, x: Float, y: Float) : Entity() {
    private var bitmap: Bitmap

    init {
        this.x = x
        this.y = y
        width = 1.0f
        height = 1.0f
        bitmap = engine.pool.createBitmap(sprite, width, height)
    }

    override fun render(canvas: Canvas, transform: Matrix, paint: Paint) {
        canvas.drawBitmap(bitmap, transform, paint)
    }
}