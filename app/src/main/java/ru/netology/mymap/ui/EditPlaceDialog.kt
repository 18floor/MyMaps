package ru.netology.mymap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import ru.netology.mymap.R
import ru.netology.mymap.data.Place
import ru.netology.mymap.databinding.FragmentEditPlaceDialogBinding
import ru.netology.mymap.utils.AndroidUtils
import ru.netology.mymap.viewmodel.PlaceViewModel

class EditPlaceDialog(val place: Place) : DialogFragment() {

    companion object {
        const val TAG = "EditPlaceDialog"
    }

    private val idPlace = place.idPlace
    private val lat = place.lat
    private val lon = place.lon

    private val placeViewModel: PlaceViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentEditPlaceDialogBinding.inflate(
            inflater,
            container,
            false
        )

        binding.placeTitle.setText(place.titlePlace)
        binding.placeDescription.setText(place.descriptionPlace)

        binding.clearButton.setOnClickListener {
            binding.apply {
                placeTitle.text.clear()
                placeDescription.text.clear()
            }
        }

        binding.btnSubmit.setOnClickListener {
            if (binding.placeTitle.text.isNotEmpty()) {

                placeViewModel.run {
                    changeContent(
                        idPlace,
                        binding.placeTitle.text.toString(),
                        binding.placeDescription.text.toString(),
                        lat,
                        lon
                    )
                    savePlace()
                    AndroidUtils.hideKeyboard(requireView())
                }
                dismiss()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.empty_title_warning),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}