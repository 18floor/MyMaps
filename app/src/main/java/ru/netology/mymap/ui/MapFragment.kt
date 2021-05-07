package ru.netology.mymap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.netology.mymap.databinding.FragmentMapBinding
import ru.netology.mymap.viewmodel.PlacesViewModel

class MapFragment : Fragment() {

    private lateinit var placesViewModel: PlacesViewModel

    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

}