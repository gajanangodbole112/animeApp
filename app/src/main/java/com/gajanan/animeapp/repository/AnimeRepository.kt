package com.gajanan.animeapp.repository

import com.gajanan.animeapp.utils.ResultApi
import com.gajanan.animeapp.model.AnimeDetailResponse
import com.gajanan.animeapp.model.TopAnimeResponse
import com.gajanan.animeapp.netwoek.RetrofitApiInterface
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class AnimeRepository
@Inject constructor(
    private val api: RetrofitApiInterface,
    ) {
    private val _getTopAnime = Channel<ResultApi<TopAnimeResponse>>()
    val getTopAnime = _getTopAnime.receiveAsFlow()
    private val _getAnimeDetails = Channel<ResultApi<AnimeDetailResponse>>()
    val getAnimeDetails = _getAnimeDetails.receiveAsFlow()
    suspend fun getAllTopAnime() {
        try {
            _getTopAnime.send(ResultApi.Loading())
            val result = api.getTopAnimes()
            if (result.isSuccessful) {
                _getTopAnime.send(ResultApi.Success(data = result.body()))
            } else {
            }
        } catch (e: Exception) {
        }
    }

    suspend fun getAnimeDetails(animeId: Int) {
        try {
            _getAnimeDetails.send(ResultApi.Loading())
            val result = api.getAnimeDetails(animeId = animeId)
            if (result.isSuccessful) {
                _getAnimeDetails.send(ResultApi.Success(data = result.body()))
            } else {
            }
        } catch (e: Exception) {
        }
    }
}