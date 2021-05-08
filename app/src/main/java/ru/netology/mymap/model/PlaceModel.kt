package ru.netology.mymap.model

import ru.netology.mymap.data.Place

data class PlaceModel(
    val places: List<Place> = emptyList(),
    val empty: Boolean = false,
    val loading: Boolean = false,
    val refreshing: Boolean = false,
    val error: Boolean = false,
)