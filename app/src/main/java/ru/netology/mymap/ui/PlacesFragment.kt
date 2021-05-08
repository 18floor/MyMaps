package ru.netology.mymap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.mymap.adapter.OnInteractionListener
import ru.netology.mymap.adapter.PlaceAdapter
import ru.netology.mymap.data.Place
import ru.netology.mymap.databinding.FragmentPlacesBinding
import ru.netology.mymap.viewmodel.PlaceViewModel

class PlacesFragment : Fragment() {

    private val placeViewModel: PlaceViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPlacesBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PlaceAdapter(object : OnInteractionListener {

            override fun onEdit(place: Place) {
                placeViewModel.editedPlace(place)
                EditPlaceDialog(place)
                    .show(childFragmentManager, EditPlaceDialog.TAG)
            }

            override fun onDelete(place: Place) {
                placeViewModel.deletePlace(place.idPlace)
            }

            override fun onMapView(place: Place) {
                super.onMapView(place)
            }

        })

        binding.listPlace.adapter = adapter

        placeViewModel.dataState.observe(viewLifecycleOwner, { state ->
            binding.progress.isVisible = state.loading
        })

        placeViewModel.data.observe(viewLifecycleOwner, { state ->
            if (state.places.isEmpty()) {
                binding.emptyList.visibility = View.VISIBLE
            }
            adapter.submitList(state.places)
        })

        return binding.root
    }
}