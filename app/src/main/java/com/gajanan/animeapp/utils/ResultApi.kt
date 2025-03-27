package com.gajanan.animeapp.utils

sealed class ResultApi<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T?) : ResultApi<T>(data)
    class Error<T>(error: Throwable?, data: T? = null) : ResultApi<T>(error = error, data = data)
    class Loading<T>(data: T? = null) : ResultApi<T>(data)
}