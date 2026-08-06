package com.example.challenge.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.domain.repository.MovieRepository
import com.example.challenge.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    // TODO: Kendi TMDB API anahtarını buraya da yapıştır
    private val apiKey = "6a70564537b9b4779e98e86d188facad"

    init {
        // Tıklanan filmin ID'sini navigasyon parametresinden otomatik alıyoruz
        savedStateHandle.get<String>("movieId")?.let { movieId ->
            getMovieDetails(movieId.toInt())
        }
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _state.value = DetailState(isLoading = true)
            when (val result = repository.getMovieDetails(movieId, apiKey)) {
                is Resource.Success -> {
                    _state.value = DetailState(movie = result.data)
                }
                is Resource.Error -> {
                    _state.value = DetailState(error = result.message ?: "Detaylar alınamadı")
                }
                else -> {}
            }
        }
    }
}