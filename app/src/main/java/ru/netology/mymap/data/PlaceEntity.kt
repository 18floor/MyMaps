package ru.netology.mymap.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val idPlace: Int,
    val titlePlace: String,
    val descriptionPlace: String?,
) {
    fun toDto(): Place {
        return Place(
            idPlace,
            titlePlace,
            descriptionPlace,
        )
    }

    companion object {
        fun fromDto(dto: Place) =
            PlaceEntity(
                dto.idPlace,
                dto.titlePlace,
                dto.descriptionPlace,
            )
    }
}

fun List<PlaceEntity>.toDto(): List<Place> = map(PlaceEntity::toDto)
fun List<Place>.toEntity(): List<PlaceEntity> = map(PlaceEntity::fromDto)