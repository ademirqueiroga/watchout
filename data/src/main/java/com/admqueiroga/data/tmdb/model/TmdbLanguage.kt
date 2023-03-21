package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class TmdbLanguage(val value: String) {
    EN_US("en-US"),
    PT_BR("pt-BR"),
}