package com.example.rawgdb.model

data class Genre (
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<GenreResults>
)

data class GenreResults(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int,
    val image_background: String,
    val games: List<GamesResults>
)
