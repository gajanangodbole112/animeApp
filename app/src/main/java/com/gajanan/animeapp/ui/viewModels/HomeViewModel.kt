package com.gajanan.animeapp.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gajanan.animeapp.repository.AnimeRepository
import com.gajanan.animeapp.ui.state.AnimeUIState
import com.gajanan.animeapp.utils.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _animeState = mutableStateOf(AnimeUIState())
    val animeState = _animeState

    init {
        viewModelScope.launch {
            repository.getTopAnime.collect {
                when (it) {
                    is ResultApi.Error -> {
                        _animeState.value = _animeState.value.copy(
                            isLoading = false,
                            isError = true
                        )
                    }

                    is ResultApi.Loading -> {
                        _animeState.value = _animeState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultApi.Success -> {
                        _animeState.value = _animeState.value.copy(
                            isLoading = false,
                            animeList = it.data?.data ?: emptyList()
                        )
                    }
                }
            }
        }
        viewModelScope.launch {
            repository.getAnimeDetails.collect {
                when (it) {
                    is ResultApi.Error -> {
                        _animeState.value = _animeState.value.copy(
                            isLoading = false,
                            isError = true
                        )
                    }

                    is ResultApi.Loading -> {
                        _animeState.value = _animeState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultApi.Success -> {
                        _animeState.value = _animeState.value.copy(
                            isLoading = false,
                            animeData = it.data?.data
                        )
                    }
                }
            }
        }
    }

    fun getAllTopAnime() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllTopAnime()
    }

    fun getAnimeDetails(animeId: Int) = viewModelScope.launch {
        repository.getAnimeDetails(animeId)
    }
}