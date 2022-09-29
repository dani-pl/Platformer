package com.danielpl.platformer.repository

import com.danielpl.platformer.local.HighScoreDao
import com.danielpl.platformer.local.entity.toHighScore
import com.danielpl.platformer.local.entity.toHighScoreEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HighScoreImpl(
    private val dao: HighScoreDao
): HighScoreRepository{
    override suspend fun insertHighScore(highScore: HighScore) {
        dao.insertHighScore(highScore.toHighScoreEntity())
    }

    override suspend fun deleteHighScore(highScore: HighScore) {
        dao.deleteHighScore(highScore.toHighScoreEntity())
    }

    override fun get4HighestScores(): Flow<List<HighScore>> {
        return dao.get4HighestScores()
                .map { entities->
                        entities.map {
                            it.toHighScore()
                            }
                        }
    }

    override fun getLongestDistance(): Flow<HighScore> {
        return dao.getLongestDistance().map {
            it.toHighScore()
        }
    }
}