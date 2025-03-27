package com.gajanan.animeapp.netwoek

import com.gajanan.animeapp.model.AnimeDetailResponse
import com.gajanan.animeapp.model.TopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiInterface {
    @GET(Endpoints.TOP_ANIME)
    suspend fun getTopAnimes() : Response<TopAnimeResponse>

    @GET("${Endpoints.ANIME}/{anime_id}")
    suspend fun getAnimeDetails(
        @Path("anime_id") animeId:Int
    ) : Response<AnimeDetailResponse>
}