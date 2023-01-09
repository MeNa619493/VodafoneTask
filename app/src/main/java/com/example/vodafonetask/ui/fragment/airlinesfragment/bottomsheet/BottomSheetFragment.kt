package com.example.vodafonetask.ui.fragment.airlinesfragment.bottomsheet

import android.app.Activity
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.vodafonetask.AirlineApplication
import com.example.vodafonetask.R
import com.example.vodafonetask.databinding.BottomSheetBinding
import com.example.vodafonetask.models.AirLineModel
import com.example.vodafonetask.util.Validation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BottomSheetViewModel by viewModels {
        BottomSheetViewModelFactory((requireActivity().application as AirlineApplication).repository)
    }

    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        val ratio = 1.0

        if (window != null && activity != null) {
            val heightDialog = (getBottomSheetDialogDefaultHeight() * 0.9f).toInt()
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (heightDialog * ratio).toInt()+30)
            window.setGravity(Gravity.BOTTOM)
        }
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            window?.setBackgroundDrawableResource(R.drawable.transparent)
            behavior.isFitToContents = false
            behavior.expandedOffset = 56.dp
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigationEvent()
        observeValidation()
        observeConfirmationButton()
        observeCancelButton()
    }

    private fun observeNavigationEvent() {
        viewModel.navigateToAirlinesFragmentEvent.observe(viewLifecycleOwner) {
            if (it) {
                dismiss()
                viewModel.doneNavigating()
            }
        }
    }

    private fun observeValidation() {
        viewModel.validationLiveEvent.observe(viewLifecycleOwner) {
            when (it) {
                Validation.NameEmpty -> {
                    showErrorSnackBar(getString(R.string.hint_title_error))
                    binding.etTitle.requestFocus()
                }

                Validation.SloganEmpty -> {
                    showErrorSnackBar(getString(R.string.hint_slogan_error))
                    binding.etSlogan.requestFocus()
                }

                Validation.CountryEmpty -> {
                    showErrorSnackBar(getString(R.string.hint_country_error))
                    binding.etCountry.requestFocus()
                }

                Validation.HeadquatersEmpty -> {
                    showErrorSnackBar(getString(R.string.hint_headquaters_error))
                    binding.etHeadQuaters.requestFocus()
                }

                Validation.EstablishedEmpty -> {
                    showErrorSnackBar(getString(R.string.hint_established_error))
                    binding.etEstablish.requestFocus()
                }
            }
        }
    }

    private fun showErrorSnackBar(message: String){
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.colorSnackBarError))
        snackBar.show()
    }

    private fun observeConfirmationButton() {
        binding.btnConfirm.setOnClickListener {
            val airline = AirLineModel(
                null,
                binding.etEstablish.toString()?: "",
                binding.etCountry.toString(),
                null,
                binding.etTitle.toString(),
                binding.etHeadQuaters.toString(),
                null,
                binding.etSlogan.toString(),
                null
            )
            viewModel.postAirline(airline)
        }
    }

    private fun observeCancelButton() {
        binding.btnCancel.setOnClickListener {
            viewModel.openAirlinesFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}