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

    fun savePlace() {
        edited.value?.let {
            viewModelScope.launch {
                try {
                    repository.savePlace(it)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun editedPlace(place: Place) {
        edited.value = place
    }

    fun changeContent(
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
                titlePlace = title,
                descriptionPlace = descriptionPlace,
                lat = lat,
                lon = lon,
            )
    }

}