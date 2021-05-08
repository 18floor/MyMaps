package ru.netology.mymap.data

data class Place(
    val idPlace: Int,
    val titlePlace: String,
    val descriptionPlace: String?,
    val lat: Double,
    val lon: Double,
)
