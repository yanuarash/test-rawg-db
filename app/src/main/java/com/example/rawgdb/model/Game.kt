package com.example.rawgdb.model

data class Games (
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<GamesResults>
)

data class GamesResults(
    val id: Int,
    val name: String,
    val slug: String,
    val added: Int,
    val name_original: String,
    val description: String,
    val rating: Float,
    val rating_top: Int,
    val ratings_count: Int,
    val released: String,
    val tba: Boolean,
    val background_image: String,
    val website: String,
    val developers: List<GamesDevelopers>,
    val short_screenshots: List<GamesShortScreenshots>
)

data class GamesShortScreenshots(
    val id: Int,
    val image :String
)

data class GamesDevelopers(
    val id: Int,
    val name: String,
    val slug: String
)
