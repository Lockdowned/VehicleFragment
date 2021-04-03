package com.example.vehiclefragment.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefragment.databinding.ItemForListFragmentBinding
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.interfaces.IFragmentCommunication
import com.example.vehiclefragment.viewmodels.VehicleViewModel

class VehicleListAdaptor(
    private val vehicleViewModel: VehicleViewModel,
    private val context: IFragmentCommunication
):
//    RecyclerView.Adapter<VehicleListAdaptor.VehicleViewHolder>(){
    ListAdapter<VehicleItem, VehicleListAdaptor.VehicleViewHolder>(VehicleComparator()) {

    inner class VehicleViewHolder(val itemForListFragmentBinding: ItemForListFragmentBinding):
            RecyclerView.ViewHolder(itemForListFragmentBinding.root){

        fun bind(vehicleItem: VehicleItem){
            itemForListFragmentBinding.textBrandsandModelItem.text = vehicleItem.brandAndModel
            itemForListFragmentBinding.textSpecificationItem.text = vehicleItem.specification
            itemForListFragmentBinding.textServInfItem.text = vehicleItem.serviceInfo
//            itemForListFragmentBinding.imageVehicleItem.setImageDrawable(vehicleItem.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemForListFragmentBinding = ItemForListFragmentBinding.inflate(layoutInflater, parent,
            false)
        return VehicleViewHolder(itemForListFragmentBinding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            val chosenVehicleItem = getItem(position)
            context.toEdit(chosenVehicleItem)
        }


    }




    class VehicleComparator: DiffUtil.ItemCallback<VehicleItem>(){
        override fun areItemsTheSame(oldItem: VehicleItem, newItem: VehicleItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VehicleItem, newItem: VehicleItem): Boolean {
            return oldItem.brandAndModel == newItem.brandAndModel &&
                    oldItem.img == newItem.img &&
                    oldItem.serviceInfo == oldItem.serviceInfo &&
                    oldItem.specification == oldItem.specification &&
                    oldItem.uriString == oldItem.uriString
        }

    }
}