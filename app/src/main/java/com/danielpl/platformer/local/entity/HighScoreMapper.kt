package com.danielpl.platformer.local.entity

import com.danielpl.platformer.repository.HighScore

fun HighScoreEntity.toHighScore(): HighScore {
    return HighScore(
        playerName = playerName,
        highScore = newScore
    )
}

fun HighScore.toHighScoreEntity(): HighScoreEntity {
    return HighScoreEntity(
        playerName = playerName,
        newScore = highScore
    )
}