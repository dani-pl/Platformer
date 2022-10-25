package com.danielpl.platformer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.danielpl.platformer.gamepad.TouchController
import com.danielpl.platformer.preferences.Preferences
import com.danielpl.platformer.repository.HighScoreRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var repository: HighScoreRepository

    lateinit var game: Game


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        game = findViewById<Game>(R.id.game)
        val input = TouchController(findViewById(R.id.touch_controller))
        game.setControls(input)
    }

    override fun onResume() {
        super.onResume()
        game.resume()
        // Old way of getting longestDistance
        // val longestDistance = preferences.getLongestDistance()
        //val highScore = findViewById<TextView>(R.id.highscore)

        // Old way of getting highScore: When just 1 score was needed
        //highScore.text = getString(R.string.highScore, longestDistance.toString())

        // New way of getting highScore: With Local Room Database
        /*
        lifecycleScope.launch {
            try {
                repository.getLongestDistance().collect {
                    highScore.text = getString(R.string.highScore, it.highScore.toString())
                }
            } catch (error: Exception) {
                Log.d("Main Activity", error.toString())
            }
        }

         */

    }

    override fun onPause() {
        super.onPause()
        game.pause()
    }
}