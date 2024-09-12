package com.example.proj1.api

import androidx.lifecycle.LiveData

class FilmsRepository {
    private val FilmsService = RetrofitInstance.api
    suspend fun getFilms(): DataFilms {
        return FilmsService.getFilms()
    }
}