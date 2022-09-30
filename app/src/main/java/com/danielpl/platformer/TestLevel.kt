package com.danielpl.platformer

import com.danielpl.platformer.util.Config.NO_TILE
import com.danielpl.platformer.util.Config.PLAYER

class TestLevel: LevelData() {
    init {
        tileToBitmap.put(NO_TILE,"no_tile")
        tileToBitmap.put(1, PLAYER)
        tileToBitmap.put(2,"snow_ground_left")
        tileToBitmap.put(3,"snow")
        tileToBitmap.put(4,"snow_ground_right")
        tileToBitmap.put(5,"snow_square")

        tiles = arrayOf(
            intArrayOf(2,3,3,3,3,3,3,4),
            intArrayOf(5,0,0,1,0,0,0,5),
            intArrayOf(5,0,0,0,0,0,0,5),
            intArrayOf(5,0,2,3,4,0,0,5),
            intArrayOf(5,0,0,0,0,0,0,5),
            intArrayOf(5,3,3,3,3,3,3,5)
        )
    }
}