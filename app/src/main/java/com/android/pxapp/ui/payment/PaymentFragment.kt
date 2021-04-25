package com.android.pxapp.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.pxapp.data.model.PaymentQrData
import com.android.pxapp.data.network.ApiState
import com.android.pxapp.databinding.FragmentPaymentBinding
import com.bumptech.glide.Glide

class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val paymentViewModel: PaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(layoutInflater, parent, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentViewModel.getPaymentQrCodeFromRepo().observe(viewLifecycleOwner, Observer {
            when(it) {
                is ApiState.Loading -> {
                binding.progressBar.visibility = VISIBLE
                }
                is ApiState.Success<*> -> {
                    binding.progressBar.visibility = GONE
                    paymentViewModel.setPaymentQrData(it.data as PaymentQrData)

                }
                is ApiState.Failed<*> -> {
                    binding.progressBar.visibility = GONE
                }
            }
        })

        paymentViewModel.getPaymentQrData().observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext())
                    .load(paymentViewModel.getQrCodeBitmap(it.paymentQrResult.qrCode)).into(binding.ivQrCode)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}