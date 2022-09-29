package com.danielpl.platformer

import android.util.SparseArray

internal const val PLAYER = "lightblue_left1"
internal const val NULLSPRITE = "nullsprite"
internal const val NO_TILE = 0

abstract class LevelData {
    var tiles: Array<IntArray> = emptyArray()
    val tileToBitmap = SparseArray<String>()

    fun getRow(y: Int): IntArray{
        return tiles[y]
    }
    fun getTile(x: Int, y:Int): Int{
        return getRow(y)[x]
    }
    fun getSpriteName(tileType: Int): String{
        val fileName = tileToBitmap[tileType]
        return fileName ?: NULLSPRITE
    }
    fun getLevelHeight(): Int{
        return tiles.size
    }
    fun getLevelWidth(): Int{
        return getRow(0).size
    }
}