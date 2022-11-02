package com.danielpl.platformer.level

import android.util.SparseArray
import com.danielpl.platformer.util.Config.NULLSPRITE


abstract class LevelData {
    var tiles = ArrayList<IntArray>()
    val tileToBitmap = SparseArray<String>()

    fun getRow(y: Int): IntArray {
        return tiles[y]
    }

    fun getSpriteName(tileType: Int): String {
        val fileName = tileToBitmap[tileType]
        return fileName ?: NULLSPRITE
    }

    fun getLevelHeight(): Int {
        return tiles.size
    }

    /*

    // Functions are never used

    fun getTile(x: Int, y:Int): Int{
        return getRow(y)[x]
    }

    fun getLevelWidth(): Int{
        return getRow(0).size
    }

     */
}