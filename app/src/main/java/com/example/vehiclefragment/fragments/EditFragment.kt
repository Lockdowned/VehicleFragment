package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.vehiclefragment.R
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.databinding.FragmentEditBinding

class EditFragment : Fragment(R.layout.fragment_edit) {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    


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
        binding.etServiceEditFragment.setText("Тут работает")
    }

    fun fillEditView(vehicleItem: VehicleItem){
        val servInf = Editable.Factory.getInstance().newEditable("vehicleItem.serviceInfo")
        binding.tvBrandTextEditFragment.text = vehicleItem.brandAndModel.plus(" " + vehicleItem.specification)
//        if (binding.etServiceEditFragment.text.isNotEmpty()){
//            binding.etServiceEditFragment.text.clear()
//        }
        binding.etServiceEditFragment.setText("Тут нет")

//        var text =  Editable.Factory.getInstance().newEditable("dsfsdf ")
//        binding.etServiceEditFragment.text = text
//        binding.etServiceEditFragment.text = text
        binding.imageViewServicePage.setImageDrawable(vehicleItem.img)
    }

}