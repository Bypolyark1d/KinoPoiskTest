package com.example.proj1.api

data class DataFilms(
    val total:Int,
    val totalPages:Int,
    var items:ArrayList<Film>
)
