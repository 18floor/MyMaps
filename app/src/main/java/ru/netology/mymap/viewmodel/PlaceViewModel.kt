package ru.netology.mymap.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.mymap.data.AppDb
import ru.netology.mymap.data.Place
import ru.netology.mymap.model.PlaceModel
import ru.netology.mymap.repository.PlaceRepository
import ru.netology.mymap.repository.PlaceRepositoryIml
import ru.netology.mymap.utils.Empty

class PlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlaceRepository =
        PlaceRepositoryIml(AppDb.getInstance(context = application).placeDao())

    val data: LiveData<PlaceModel> = repository.data.map(::PlaceModel)
    private val edited = MutableLiveData(Empty.emptyPlace)

    private val _dataState = MutableLiveData<PlaceModel>()
    val dataState: LiveData<PlaceModel>
        get() = _dataState

    fun savePlace() {
        edited.value?.let {
            viewModelScope.launch {
                try {
                    _dataState.value = PlaceModel(loading = true)
                    repository.savePlace(it)
                    _dataState.value = PlaceModel()
                } catch (e: Exception) {
                    _dataState.value = PlaceModel(error = true)
                }
            }
        }
        edited.value = Empty.emptyPlace
    }

    fun deletePlace(idPlace: Int) = viewModelScope.launch {
        try {
            repository.deletePlace(idPlace)
            _dataState.value = PlaceModel()
        } catch (e: Exception) {
            _dataState.value = PlaceModel(error = true)
        }
    }

    fun editedPlace(place: Place) {
        edited.value = place
    }

    fun changeContent(
        idPlace: Int,
        titlePlace: String,
        descriptionPlace: String?,
        lat: Double,
        lon: Double
    ) {
        val title = titlePlace.trim()
        if (edited.value?.titlePlace == title) {
            return
        }
        edited.value =
            edited.value?.copy(
                idPlace = idPlace,
                titlePlace = title,
                descriptionPlace = descriptionPlace,
                lat = lat,
                lon = lon,
            )
    }
}