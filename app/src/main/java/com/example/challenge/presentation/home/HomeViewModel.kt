package com.example.challenge.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.domain.repository.MovieRepository
import com.example.challenge.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    // Arayüzün dinleyeceği State (Durum) nesnesi
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    // TODO: Kendi TMDB API anahtarını buraya yapıştırmalısın
    private val apiKey = "6a70564537b9b4779e98e86d188facad"

    init {
        // ViewModel ilk oluştuğunda tüm filmleri çekmeye başla
        getAllMovies()
    }

    private fun getAllMovies() {
        getNowPlaying()
        getPopular()
        getTopRated()
        getUpcoming()
    }

    private fun getNowPlaying() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.getNowPlaying(1, apiKey)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        nowPlayingMovies = result.data?.results ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Beklenmeyen bir hata oluştu",
                        isLoading = false
                    )
                }
                else -> {}
            }
        }
    }

    private fun getPopular() {
        viewModelScope.launch {
            when (val result = repository.getPopular(1, apiKey)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        popularMovies = result.data?.results ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    // Sadece nowPlaying'de loading/error yönettik, dilersek buraya da ekleyebiliriz.
                }
                else -> {}
            }
        }
    }

    private fun getTopRated() {
        viewModelScope.launch {
            when (val result = repository.getTopRated(1, apiKey)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        topRatedMovies = result.data?.results ?: emptyList()
                    )
                }
                is Resource.Error -> {}
                else -> {}
            }
        }
    }

    private fun getUpcoming() {
        viewModelScope.launch {
            when (val result = repository.getUpcoming(1, apiKey)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        upcomingMovies = result.data?.results ?: emptyList()
                    )
                }
                is Resource.Error -> {}
                else -> {}
            }
        }
    }
}