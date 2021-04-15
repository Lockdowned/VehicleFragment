package com.example.vehiclefragment.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vehiclefragment.databinding.ItemForListFragmentBinding
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.interfaces.IFragmentCommunication
import com.example.vehiclefragment.viewmodels.VehicleViewModel

class VehicleListAdaptor(
    private val vehicleViewModel: VehicleViewModel,
    private val context: Context
): ListAdapter<VehicleItem, VehicleListAdaptor.VehicleViewHolder>(VehicleComparator()) {

    inner class VehicleViewHolder(private val itemForListFragmentBinding: ItemForListFragmentBinding):
            RecyclerView.ViewHolder(itemForListFragmentBinding.root){

        fun bind(vehicleItem: VehicleItem){
            itemForListFragmentBinding.textBrandsandModelItem.text = vehicleItem.brandAndModel
            itemForListFragmentBinding.textSpecificationItem.text = vehicleItem.specification
            itemForListFragmentBinding.textServInfItem.text = vehicleItem.serviceInfo
            vehicleItem.img?.let {
                Glide.with(context).load(vehicleItem.img).into(itemForListFragmentBinding.imageVehicleItem) }
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
            vehicleViewModel.selectedIdVehicle = getItem(position).id
            (context as IFragmentCommunication).toEdit()
        }
    }




    class VehicleComparator: DiffUtil.ItemCallback<VehicleItem>(){
        override fun areItemsTheSame(oldItem: VehicleItem, newItem: VehicleItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VehicleItem, newItem: VehicleItem): Boolean {
            return oldItem.brandAndModel == newItem.brandAndModel &&
                    oldItem.img == newItem.img &&
                    oldItem.serviceInfo == newItem.serviceInfo &&
                    oldItem.specification == newItem.specification
        }

    }
}