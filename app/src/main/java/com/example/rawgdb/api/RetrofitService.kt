package com.example.rawgdb.api

import com.example.rawgdb.model.Games
import com.example.rawgdb.model.GamesResults
import com.example.rawgdb.model.Genre
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.rawg.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

        //key API rawg.io
        fun getKey(): String {
            return "40f1e28385dd412e8b0bec53d52d8734"
        }
    }

    @GET("genres")
    suspend fun getGenres(
        @Query("key") key: String?,
        @Query("ordering") ordering: String?,
        @Query("page") page: Int,
        @Query("page_size") page_size: Int
    ): Response<Genre>

    @GET("games")
    suspend fun getGenresDetail(
        @Query("key") key: String?,
        @Query("genres") genre: Int,
        @Query("ordering") ordering: String?,
        @Query("page") page: Int,
        @Query("page_size") page_size: Int
    ): Response<Games>

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") key: String?
    ): Response<GamesResults>
}