package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefragment.R
import com.example.vehiclefragment.adaptor.VehicleListAdaptor
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.helperObject.InitHelp
import com.example.vehiclefragment.interfaces.IVehicleToEditListener

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var localContext: Context

    private lateinit var vehicleList: MutableList<VehicleItem>
    private var vehicleAdapter: VehicleListAdaptor? = null
    private lateinit var fragment: RecyclerView

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

        if (vehicleAdapter == null){
            vehicleList = InitHelp.initialize(localContext)
            vehicleAdapter = VehicleListAdaptor(vehicleList, localContext as IVehicleToEditListener)
        }
        
        fragment = view.findViewById<RecyclerView>(R.id.rvListFragment)
        fragment.adapter = vehicleAdapter
        fragment.layoutManager = LinearLayoutManager(localContext)
    }

    fun addNewVehicleItem(vehicleItem: VehicleItem){
        vehicleList.add(vehicleItem)
        vehicleAdapter?.notifyItemInserted(vehicleList.size - 1)
    }

    fun updateVehicleItems(){
        vehicleAdapter?.notifyDataSetChanged()
    }

}