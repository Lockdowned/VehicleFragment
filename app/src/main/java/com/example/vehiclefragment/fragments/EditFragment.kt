package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.vehiclefragment.R
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.databinding.FragmentEditBinding
import com.example.vehiclefragment.interfaces.IFragmentCommunication
import com.example.vehiclefragment.viewmodels.VehicleViewModel

class EditFragment(private val vehicleViewModel: VehicleViewModel) : Fragment(R.layout.fragment_edit) {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private var chosenItemVehicle: VehicleItem? = null

    lateinit var localContext: IFragmentCommunication

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context as IFragmentCommunication
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null){
            _binding = FragmentEditBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chosenItemVehicle = vehicleViewModel.getSelected()

//        binding.imageViewServicePage.setImageDrawable(chosenItemVehicle?.img)
        binding.tvBrandTextEditFragment.text = chosenItemVehicle?.brandAndModel
            .plus(chosenItemVehicle?.specification)

        binding.btnEditOnPageService.setOnClickListener {
            chosenItemVehicle?.let {
                it.serviceInfo = binding.etServiceEditFragment.text.toString()
                vehicleViewModel.update(it)
                localContext.toList()
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        chosenItemVehicle?.let { binding.etServiceEditFragment.setText(it.serviceInfo) }
    }

}