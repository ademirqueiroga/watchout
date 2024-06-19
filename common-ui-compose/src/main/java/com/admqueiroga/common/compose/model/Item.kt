package com.admqueiroga.common.compose.model

import androidx.compose.runtime.Immutable

@Immutable
data class Item(
    val id: Long = 0L,
    val title: String = "",
    val subtitle: String = "",
    val rating: Float = 0F,
    val image: String = "",
    val backdropImage: String = "",
)

