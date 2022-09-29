package com.danielpl.platformer.preferences

import android.content.SharedPreferences
import com.danielpl.platformer.R
import com.danielpl.platformer.preferences.Preferences.Companion.LONGEST_DIST
import com.danielpl.platformer.preferences.Preferences.Companion.PLAYER_NAME

class DefaultPreferences(
    private val sharedPref: SharedPreferences
) : Preferences {
    override fun saveLongestDistance(distance: Int) {
        sharedPref.edit()
            .putInt(LONGEST_DIST, distance)
            .apply()
    }

    override fun getLongestDistance(): Int {
        return sharedPref.getInt(LONGEST_DIST, 0)
    }

    override fun savePlayerName(playerName: String) {
        sharedPref.edit()
            .putString(PLAYER_NAME, playerName)
            .apply()
    }

    override fun getPlayerName(): String? {
        return sharedPref.getString(PLAYER_NAME, R.string.player_name_example.toString())
    }
}