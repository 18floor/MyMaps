package ru.netology.mymap.repository

import androidx.lifecycle.LiveData
import ru.netology.mymap.data.Place

interface PlaceRepository {
    val data: LiveData<List<Place>>
    suspend fun deletePlace(idPlace: Int)
    suspend fun getPlace(idPlace: Int): Place
    suspend fun savePlace(place: Place)
}