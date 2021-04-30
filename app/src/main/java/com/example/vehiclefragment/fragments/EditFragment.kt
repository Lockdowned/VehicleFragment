package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.vehiclefragment.R
import com.example.vehiclefragment.adaptors.TaskListAdaptor
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.databinding.FragmentEditBinding
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.viewmodels.TaskViewModel
import com.example.vehiclefragment.viewmodels.VehicleViewModel

class EditFragment(private val taskViewModel: TaskViewModel,
                    private val vehicleViewModel: VehicleViewModel)
    : Fragment(R.layout.fragment_edit) {

    private lateinit var  binding: FragmentEditBinding

    var chosenItemVehicle: VehicleItem? = null

    private lateinit var localContext: Context

    private var taskListAdaptor: TaskListAdaptor? = null

    private lateinit var textWatcherEditText: TextWatcher

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized){
            binding = FragmentEditBinding.inflate(inflater, container, false)
        }
        chosenItemVehicle = null
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vehicleViewModel.selectedIdVehicle?.let {
            taskViewModel.setVehicleWithTasks(it)
        }

        binding.buttonAddTask.setOnClickListener {
            chosenItemVehicle!!.serviceInfo = binding.etServiceEditFragment.text.toString()
            if (binding.etTextTaskToAdd.text.isNotEmpty()){
                val task = TaskItem(
                        false,
                        binding.etTextTaskToAdd.text.toString(),
                        chosenItemVehicle?.id
                )
                taskViewModel.insertTask(task)
                binding.etTextTaskToAdd.setText("")
            }
        }

        textWatcherEditText = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(text: Editable?) {
                chosenItemVehicle?.let {
                    it.serviceInfo = text.toString()
                    vehicleViewModel.update(chosenItemVehicle!!)
                }
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        taskViewModel.vehicleWithTasks?.observe(viewLifecycleOwner, { list -> // странное решение
            if (chosenItemVehicle == null){
                chosenItemVehicle = list.first().vehicle
                binding.run {
                    tvBrandTextEditFragment.text = chosenItemVehicle?.brandAndModel.plus("\n")
                            .plus(chosenItemVehicle?.specification)
                    etServiceEditFragment.setText(chosenItemVehicle?.serviceInfo)
                    if (chosenItemVehicle!!.img != -1) {
                        Glide.with(localContext).load(vehicleViewModel.getImg(chosenItemVehicle!!.id!!)).
                        into(imageViewServicePage)
                    }
                }
            }
            taskListAdaptor = TaskListAdaptor(list.first().tasks as MutableList<TaskItem>, taskViewModel)
            binding.rvTaskList.adapter = taskListAdaptor
            binding.rvTaskList.layoutManager = LinearLayoutManager(localContext)
        })
        binding.etTextTaskToAdd.setText("")

        binding.etServiceEditFragment.addTextChangedListener(textWatcherEditText)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.etServiceEditFragment.removeTextChangedListener(textWatcherEditText)
    }
}