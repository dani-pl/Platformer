package com.danielpl.platformer.entity.animation

import android.graphics.Bitmap


class Animation(val frames: MutableList<Frame>) {

    private var currentFrameNumber = 0
    private var cyclesCounter = 0


    fun getBitmapToRender(): Bitmap {
        return frames[currentFrameNumber].bitmap
    }

    fun update() {
        val currentFrame = frames[currentFrameNumber]
        if (currentFrame.gameCyclesDuration == cyclesCounter) {
            currentFrameNumber = nextFrameNumber(currentFrameNumber)
            cyclesCounter = 0
        }
        cyclesCounter++
    }

    private fun nextFrameNumber(frameNumber: Int): Int {
        return if (frameNumber == (frames.size - 1)) {
            0
        } else {
            frameNumber + 1
        }
    }
}