package com.danielpl.platformer.gamepad

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.danielpl.platformer.R
import com.danielpl.platformer.util.Config.isGameOver
import com.danielpl.platformer.util.Config.isLevelSuccessful
import com.danielpl.platformer.util.Config.restart

@SuppressLint("ClickableViewAccessibility")
class TouchController(view: View) : InputManager(), View.OnTouchListener {

    init {
        view.findViewById<Button>(R.id.gamepad_up)
            .setOnTouchListener(this)
        view.findViewById<Button>(R.id.gamepad_down)
            .setOnTouchListener(this)
        view.findViewById<Button>(R.id.gamepad_left)
            .setOnTouchListener(this)
        view.findViewById<Button>(R.id.gamepad_right)
            .setOnTouchListener(this)
        view.findViewById<Button>(R.id.gamepad_jump)
            .setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val action = event.actionMasked
        val id: Int = v.id
        if (action == MotionEvent.ACTION_DOWN) {
            // player is pressing a "button"
            if (id == R.id.gamepad_up) {
                verticalFactor -= 1
            } else if (id == R.id.gamepad_down) {
                verticalFactor += 1
            }
            if (id == R.id.gamepad_left) {
                horizontalFactor -= 1
            } else if (id == R.id.gamepad_right) {
                horizontalFactor += 1
            }
            if (id == R.id.gamepad_jump) {
                isJumping = true
            }
            if (isGameOver) {
                restart = true
            }
            if (isLevelSuccessful) {
                restart = true
            }
        } else if (action == MotionEvent.ACTION_UP) {
            // player released a "button"
            if (id == R.id.gamepad_up) {
                verticalFactor += 1
            } else if (id == R.id.gamepad_down) {
                verticalFactor -= 1
            }
            if (id == R.id.gamepad_left) {
                horizontalFactor += 1
            } else if (id == R.id.gamepad_right) {
                horizontalFactor -= 1
            }
            if (id == R.id.gamepad_jump) {
                isJumping = false
            }
        }
        return false
    }
}