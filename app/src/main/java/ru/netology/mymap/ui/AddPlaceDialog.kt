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
import ru.netology.mymap.databinding.FragmentAddPlaceDialogBinding
import ru.netology.mymap.utils.AndroidUtils
import ru.netology.mymap.viewmodel.PlaceViewModel

class AddPlaceDialog(
    private val lat: Double,
    private val lon: Double,
) : DialogFragment() {

    companion object {
        const val TAG = "AddPlaceDialog"
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

        val binding = FragmentAddPlaceDialogBinding.inflate(
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
                        0,
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