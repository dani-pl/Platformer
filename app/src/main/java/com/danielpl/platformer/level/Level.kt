package com.danielpl.platformer.level

import android.content.Context
import android.content.res.AssetManager
import com.danielpl.platformer.util.Config.NO_TILE

import com.danielpl.platformer.util.Config.totalCollectibles
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class Level(levelNameFile: String, assetManager: AssetManager): LevelData() {


    init {

        if(levelNameFile=="v1_lv1.txt"){
            tileToBitmap.put(NO_TILE,"no_tile")
            tileToBitmap.put(1, "lightblue_left1")
            tileToBitmap.put(2,"snow_ground_left")
            tileToBitmap.put(3,"snow")
            tileToBitmap.put(4,"snow_ground_right")
            tileToBitmap.put(5,"snow_square")
            tileToBitmap.put(6,"spearsdown_brown")
            tileToBitmap.put(7,"coinyellow")
        } else {
            tileToBitmap.put(NO_TILE,"no_tile")
            tileToBitmap.put(1, "brown_left1")
            tileToBitmap.put(2,"desert_ground_left")
            tileToBitmap.put(3,"desert")
            tileToBitmap.put(4,"desert_ground_right")
            tileToBitmap.put(5,"desert_square")
            tileToBitmap.put(6,"enemyblockbrown")
            tileToBitmap.put(7,"coinyellow")
        }



        val inputStream =assetManager.open("level/$levelNameFile")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        bufferedReader.forEachLine { it ->
            tiles.add(it.split(",").map{it.toInt()}.toIntArray())
        }


        totalCollectibles = 0
        for (array in tiles) {
            totalCollectibles+= array.count{it==7}
        }

    }
}