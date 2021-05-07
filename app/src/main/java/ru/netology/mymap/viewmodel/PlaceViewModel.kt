package ru.netology.mymap.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.mymap.data.AppDb
import ru.netology.mymap.model.PlaceModel
import ru.netology.mymap.repository.PlaceRepository
import ru.netology.mymap.repository.PlaceRepositoryIml

class PlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlaceRepository =
        PlaceRepositoryIml(AppDb.getInstance(context = application).placeDao())

    val data: LiveData<PlaceModel> = repository.data.map(::PlaceModel)
}