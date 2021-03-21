package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vehiclefragment.R
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.databinding.FragmentEditBinding
import com.example.vehiclefragment.interfaces.IUpdateListListener
import com.example.vehiclefragment.interfaces.IVehicleCreatedVehicleListener

class EditFragment : Fragment(R.layout.fragment_edit) {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private var chosenItemVehicle:VehicleItem? = null

    lateinit var localContext: IUpdateListListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context as IUpdateListListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null){
            _binding = FragmentEditBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEditOnPageService.setOnClickListener {
            chosenItemVehicle?.let {
                it.serviceInfo = binding.etServiceEditFragment.text.toString()
                localContext.updateListFragment()
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        chosenItemVehicle?.let { binding.etServiceEditFragment.setText(it.serviceInfo) }
    }




    fun fillEditView(vehicleItem: VehicleItem){
        chosenItemVehicle = vehicleItem
        binding.tvBrandTextEditFragment.text = vehicleItem.brandAndModel.plus(" " + vehicleItem.specification)
        binding.imageViewServicePage.setImageDrawable(vehicleItem.img)
    }

}