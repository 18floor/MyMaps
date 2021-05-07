package ru.netology.mymap.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {

    @Query("SELECT * FROM PlaceEntity ORDER BY titlePlace ASC")
    fun getAll(): LiveData<List<PlaceEntity>>

    @Query("DELETE FROM PlaceEntity WHERE idPlace = :idPlace")
    suspend fun deletePlace(idPlace: Int)

    @Query("SELECT * FROM PlaceEntity WHERE idPlace = :idPlace")
    suspend fun getPlace(idPlace: Int): Place

    @Query("UPDATE PlaceEntity SET titlePlace = :titlePlace, descriptionPlace = :descriptionPlace WHERE idPlace = :idPlace")
    suspend fun updatePlace(idPlace: Int, titlePlace: String, descriptionPlace: String?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceEntity)

    suspend fun savePlace(place: PlaceEntity) =
        if (place.idPlace == 0) insertPlace(place) else updatePlace(
            place.idPlace,
            place.titlePlace,
            place.descriptionPlace
        )
}