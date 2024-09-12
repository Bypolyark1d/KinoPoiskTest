package com.example.proj1.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FilmViewModel :ViewModel() {
    private val repository = FilmsRepository()
    private val _films = MutableLiveData<ArrayList<Film>>()
    val films: LiveData<ArrayList<Film>> = _films
    fun fetchfilms() {
        viewModelScope.launch {
            try {
                val arrFilm = repository.getFilms()
                _films.value = arrFilm.items
            } catch (e: Exception) {
            }
        }
    }
}