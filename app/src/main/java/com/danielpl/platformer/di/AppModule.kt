package com.danielpl.platformer.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.danielpl.platformer.local.HighScoreDatabase
import com.danielpl.platformer.preferences.DefaultPreferences
import com.danielpl.platformer.preferences.Preferences
import com.danielpl.platformer.repository.HighScoreImpl
import com.danielpl.platformer.repository.HighScoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideHighScoreDatabase(app: Application): HighScoreDatabase {
        return Room.databaseBuilder(
            app,
            HighScoreDatabase::class.java,
            "highScore_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(
        db: HighScoreDatabase
    ): HighScoreRepository {
        return HighScoreImpl(
            dao = db.dao
        )
    }


}