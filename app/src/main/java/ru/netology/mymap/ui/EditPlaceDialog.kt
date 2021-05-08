package ru.netology.mymap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ru.netology.mymap.databinding.FragmentEditPlaceDialogBinding
import ru.netology.mymap.utils.AndroidUtils
import ru.netology.mymap.viewmodel.PlaceViewModel

class EditPlaceDialog(val lat: Double, val lon: Double, val idPlace: Int) : DialogFragment() {

    companion object {
        const val TAG = "EditPlaceDialog"
    }

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
                        binding.placeTitle.text.toString(),
                        binding.placeDescription.text.toString(),
                        lat,
                        lon
                    )
                    savePlace()
                    AndroidUtils.hideKeyboard(requireView())
                }
            }
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}