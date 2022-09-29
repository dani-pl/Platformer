package com.danielpl.platformer.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danielpl.platformer.local.entity.HighScoreEntity

@Database(
    entities = [HighScoreEntity::class],
    version = 1
)
abstract class HighScoreDatabase : RoomDatabase() {
    abstract val dao: HighScoreDao
}