package ru.netology.mymap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.mymap.databinding.FragmentPlacesBinding
import ru.netology.mymap.viewmodel.PlaceViewModel

class PlacesFragment : Fragment() {

    private val placeViewModel: PlaceViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val binding by lazy { FragmentPlacesBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        placeViewModel.data.observe(viewLifecycleOwner, { state ->
            //TODO
        })


        return binding.root
    }

}