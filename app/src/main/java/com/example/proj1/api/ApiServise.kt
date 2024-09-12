package com.example.proj1.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers(
        "X-API-KEY: ffdb3122-6cfb-4551-a615-7bae945ea9ae",
        "Content-Type: application/json")
    @GET("films?order=RATING&type=ALL&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000&page=1")
    suspend fun getFilms():DataFilms
}