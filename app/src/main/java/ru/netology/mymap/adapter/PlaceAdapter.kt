package ru.netology.mymap.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.netology.mymap.data.Place

interface OnInteractionListener {
    fun onEdit(place: Place) {}
    fun onDelete(place: Place) {}
    fun onMapView(place: Place) {}
}

class PlaceAdapter(
    private val onInteractionListener: OnInteractionListener
) {
}

class PostDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.idPlace == newItem.idPlace
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }
}