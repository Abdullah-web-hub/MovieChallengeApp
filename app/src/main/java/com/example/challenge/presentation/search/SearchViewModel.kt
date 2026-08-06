package com.example.challenge.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.domain.repository.MovieRepository
import com.example.challenge.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    // TODO: Kendi TMDB API anahtarını buraya da yapıştırmalısın
    private val apiKey = "6a70564537b9b4779e98e86d188facad"

    private var searchJob: Job? = null

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        searchJob?.cancel() // Eğer kullanıcı hala yazıyorsa önceki aramayı iptal et

        searchJob = viewModelScope.launch {
            delay(500L) // Kullanıcı yazmayı bitirene kadar yarım saniye bekle
            if (query.isNotBlank()) {
                searchMovies(query)
            } else {
                _state.value = _state.value.copy(searchResults = emptyList())
            }
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.searchMovies(query, 1, apiKey)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        searchResults = result.data?.results ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Arama sırasında bir hata oluştu",
                        isLoading = false
                    )
                }
                else -> {}
            }
        }
    }
}