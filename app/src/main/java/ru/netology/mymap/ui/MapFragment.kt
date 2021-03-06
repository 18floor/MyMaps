package ru.netology.mymap.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitAnimateCamera
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.utils.collection.addMarker
import ru.netology.mymap.R
import ru.netology.mymap.databinding.FragmentMapBinding
import ru.netology.mymap.utils.StringArg
import ru.netology.mymap.viewmodel.PlaceViewModel

class MapFragment : Fragment() {

    private val placeViewModel: PlaceViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private lateinit var googleMap: GoogleMap

    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                googleMap.apply {
                    isMyLocationEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.gps_not_allowed),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        lifecycle.coroutineScope.launchWhenCreated {
            googleMap = mapFragment.awaitMap().apply {
                isTrafficEnabled = false
                isBuildingsEnabled = true

                uiSettings.apply {
                    isZoomControlsEnabled = true
                    setAllGesturesEnabled(true)
                }
            }

            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    googleMap.apply {
                        isMyLocationEnabled = true
                        uiSettings.isMyLocationButtonEnabled = true
                    }

                    val fusedLocationProviderClient = LocationServices
                        .getFusedLocationProviderClient(requireActivity())

                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                        println(it)
                    }
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    //TODO
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }

            placeViewModel.data.observe(viewLifecycleOwner, { state ->

                if (state.places.isEmpty()) {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.warning))
                        .setIcon(R.drawable.ic_alert_24)
                        .setMessage(getString(R.string.empty_map_dialog))
                        .setPositiveButton("??????????????") { _, _ -> }
                        .create()
                        .show()
                }

                val markerManager = MarkerManager(googleMap)
                val collection: MarkerManager.Collection = markerManager.newCollection().apply {
                    state.places.forEach { place ->
                        addMarker {
                            position(LatLng(place.lat, place.lon))
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                            title(place.titlePlace)
                            snippet(place.descriptionPlace)
                            infoWindowAnchor(0.5F, 0F)
                        }
                    }
                }

                collection.setOnMarkerClickListener { marker ->
                    marker.showInfoWindow()
                    true
                }

                val boundsBuilder = LatLngBounds.Builder()
                if (state.places.isNotEmpty()) {
                    state.places.forEach { place ->
                        boundsBuilder.include(LatLng(place.lat, place.lon))
                    }
                    val bounds = boundsBuilder.build()
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))
                }

                collection.setOnInfoWindowClickListener {
                    googleMap.moveCamera(
                        CameraUpdateFactory.newCameraPosition(
                            cameraPosition {
                                target(it.position)
                                zoom(15F)
                            }
                        ))
                }
            })

            googleMap.setOnMapLongClickListener { point ->
                AddPlaceDialog(point.latitude, point.longitude)
                    .show(childFragmentManager, AddPlaceDialog.TAG)
            }

            val coordinatesString = arguments?.textArg?.split(",")?.toTypedArray()

            val coordinates = coordinatesString?.get(0)?.let {
                LatLng(it.toDouble(), coordinatesString[1].toDouble())
            }

            if (coordinates != null) {
                googleMap.moveCamera(
                    CameraUpdateFactory.newCameraPosition(
                        cameraPosition {
                            target(coordinates)
                            zoom(15F)
                        }
                    ))
            }
        }
    }
}