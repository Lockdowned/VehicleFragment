package com.example.vehiclefragment.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefragment.MainActivity
import com.example.vehiclefragment.R
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.databinding.ItemForListFragmentBinding
import com.example.vehiclefragment.interfaces.IVehicleCreateListener
import com.example.vehiclefragment.interfaces.IVehicleEditListener

class VehicleListAdaptor(private val vehicleList: List<VehicleItem>, private val context: IVehicleEditListener):
    RecyclerView.Adapter<VehicleListAdaptor.VehicleViewHolder>(){

    inner class VehicleViewHolder(val itemForListFragmentBinding: ItemForListFragmentBinding):
            RecyclerView.ViewHolder(itemForListFragmentBinding.root){

        fun bind(vehicleItem: VehicleItem){
            itemForListFragmentBinding.textBrandsandModelItem.text = vehicleItem.brandAndModel
            itemForListFragmentBinding.textSpecificationItem.text = vehicleItem.specification
            itemForListFragmentBinding.textServInfItem.text = vehicleItem.serviceInfo
            itemForListFragmentBinding.imageVehicleItem.setImageDrawable(vehicleItem.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemForListFragmentBinding = ItemForListFragmentBinding.inflate(layoutInflater, parent,
            false)
        return VehicleViewHolder(itemForListFragmentBinding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicleList[position])
        holder.itemView.setOnClickListener {
            val chosenVehicleItem = vehicleList[position]
            context.itemToEdit(chosenVehicleItem)
        }


    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }
}