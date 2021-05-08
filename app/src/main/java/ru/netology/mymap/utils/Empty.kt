package ru.netology.mymap.utils

import ru.netology.mymap.data.Place

object Empty {
    val emptyPlace = Place(
        idPlace = 0,
        titlePlace = "",
        descriptionPlace = "",
        lat = 0.00,
        lon = 0.00,
    )
}