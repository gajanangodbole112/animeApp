package com.gajanan.animeapp.ui.state

import com.gajanan.animeapp.model.AnimeResponse

data class AnimeUIState(
    val animeList: List<AnimeResponse> = emptyList(),
    val animeData: AnimeResponse? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)