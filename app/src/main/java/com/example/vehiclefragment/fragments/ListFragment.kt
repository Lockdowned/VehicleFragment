package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefragment.R
import com.example.vehiclefragment.adaptors.VehicleListAdaptor
import com.example.vehiclefragment.interfaces.IFragmentCommunication
import com.example.vehiclefragment.viewmodels.VehicleViewModel

class ListFragment(private val vehicleViewModel: VehicleViewModel) : Fragment(R.layout.fragment_list) {

    private lateinit var localContext: Context

    var vehicleAdapter: VehicleListAdaptor? = null
    private lateinit var fragment: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (vehicleAdapter == null){
            vehicleAdapter = VehicleListAdaptor(vehicleViewModel, localContext)
        }
        
        fragment = view.findViewById<RecyclerView>(R.id.rvListFragment)
        fragment.adapter = vehicleAdapter
        fragment.layoutManager = LinearLayoutManager(localContext)

        vehicleViewModel.allImages.observe(
                viewLifecycleOwner,
                Observer { imgesList ->
                    vehicleViewModel.currentListImg = imgesList
                    vehicleAdapter?.notifyDataSetChanged()
                }
        )

        vehicleViewModel.allVehicle.observe(
                viewLifecycleOwner,
                Observer { vehiclesList ->
                    vehiclesList?.let {
                    vehicleAdapter?.submitList(it)
                }
            }
        )
    }
}