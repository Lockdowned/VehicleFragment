package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefragment.MainActivity
import com.example.vehiclefragment.R
import com.example.vehiclefragment.adaptor.VehicleListAdaptor
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.helperObject.InitHelp

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var localContext: Context

    private lateinit var vehicleList: MutableList<VehicleItem>
    private lateinit var vehicleAdapter: VehicleListAdaptor

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vehicleList = InitHelp.initialize(localContext)
        vehicleAdapter = VehicleListAdaptor(vehicleList)

        val fragment = view.findViewById<RecyclerView>(R.id.rvListFragment)
        fragment.adapter = vehicleAdapter
        fragment.layoutManager = LinearLayoutManager(localContext)
    }

    fun addNewVehicleItem(vehicleItem: VehicleItem){
        vehicleList.add(vehicleItem)
//        vehicleAdapter.notifyItemInserted(vehicleList.size - 1)
        vehicleAdapter.notifyDataSetChanged()
    }


}