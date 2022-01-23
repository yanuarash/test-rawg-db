package com.example.rawgdb.repository

import com.example.rawgdb.api.RetrofitService

class Repository constructor(private val retrofitService: RetrofitService) {

    suspend fun getGenres(key:String, ordering: String, page: Int, page_size: Int) = retrofitService.getGenres(key, ordering, page, page_size)
    suspend fun getGenresDetail(key:String, genre: Int, ordering: String, page: Int, page_size: Int) = retrofitService.getGenresDetail(key, genre,ordering, page, page_size)
    suspend fun getGameDetail(key:String, id: Int) = retrofitService.getGameDetail(id, key)

}