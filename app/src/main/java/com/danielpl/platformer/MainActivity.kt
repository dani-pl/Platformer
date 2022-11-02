package com.danielpl.platformer


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.danielpl.platformer.gamepad.TouchController

class MainActivity : AppCompatActivity() {

    lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        game = findViewById(R.id.game)
        val input = TouchController(findViewById(R.id.touch_controller))
        game.setControls(input)
    }

    override fun onResume() {
        super.onResume()
        game.resume()
    }

    override fun onPause() {
        super.onPause()
        game.pause()
    }
}