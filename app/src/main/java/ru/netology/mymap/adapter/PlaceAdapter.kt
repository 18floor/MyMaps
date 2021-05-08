package ru.netology.mymap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.mymap.data.Place
import ru.netology.mymap.databinding.CardPlaceBinding

interface OnInteractionListener {
    fun onEdit(place: Place) {}
    fun onDelete(place: Place) {}
    fun onMapView(place: Place) {}
}

class PlaceAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Place, PlaceViewHolder>(PostDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = CardPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }
}

class PlaceViewHolder(
    private val binding: CardPlaceBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(place: Place) {

        binding.apply {
            placeTitle.text = place.titlePlace
            placeDescription.text = place.descriptionPlace

            placeIcon.setOnClickListener {
                onInteractionListener.onMapView(place)
            }

            placeView.setOnClickListener {
                onInteractionListener.onEdit(place)
            }

            deleteButton.setOnClickListener {
                onInteractionListener.onDelete(place)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.idPlace == newItem.idPlace
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }
}