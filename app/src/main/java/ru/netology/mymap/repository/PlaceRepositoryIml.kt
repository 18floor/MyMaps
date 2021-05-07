package ru.netology.mymap.repository

import androidx.lifecycle.*
import ru.netology.mymap.data.Place
import ru.netology.mymap.data.PlaceDao
import ru.netology.mymap.data.PlaceEntity
import ru.netology.mymap.data.toDto
import ru.netology.mymap.model.UnknownError

class PlaceRepositoryIml(private val dao: PlaceDao) : PlaceRepository {

    override val data = dao.getAll().map(List<PlaceEntity>::toDto)

    override suspend fun deletePlace(idPlace: Int) {
        try {
            dao.deletePlace(idPlace)
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getPlace(idPlace: Int): Place {
        try {
            return dao.getPlace(idPlace)
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun savePlace(place: Place) {
        try {
            dao.insertPlace(PlaceEntity.fromDto(place))
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}