package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehiclefragment.R
import com.example.vehiclefragment.adaptors.TaskListAdaptor
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.databinding.FragmentEditBinding
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.viewmodels.TaskViewModel
import com.example.vehiclefragment.viewmodels.VehicleViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditFragment(private val taskViewModel: TaskViewModel,
                    private val vehicleViewModel: VehicleViewModel)
    : Fragment(R.layout.fragment_edit) {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    var chosenItemVehicle: VehicleItem? = null

    private lateinit var localContext: Context

    private var taskListAdaptor: TaskListAdaptor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null){
            _binding = FragmentEditBinding.inflate(inflater, container, false)
        }
        chosenItemVehicle = null
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        taskViewModel.chosenVehicleId?.let {
            taskViewModel.setVehicleWithTasks(it)
        }

//        chosenItemVehicle = taskViewModel.vehicleWithTasks?.value?.first()?.vehicle//ПОЧЕМУ null??

        taskViewModel.vehicleWithTasks?.observe(viewLifecycleOwner, {
            chosenItemVehicle = it.first().vehicle

            taskListAdaptor = TaskListAdaptor(it.first().tasks as MutableList<TaskItem>, taskViewModel)
            binding.rvTaskList.adapter = taskListAdaptor
            binding.rvTaskList.layoutManager = LinearLayoutManager(localContext)
        })

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
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        lifecycleScope.launch { // такое решение не очень
            while (true){
                delay(1)
                if (chosenItemVehicle != null) break
            }
//            binding.imageViewServicePage.setImageDrawable(chosenItemVehicle?.img)
            binding.tvBrandTextEditFragment.text = chosenItemVehicle?.brandAndModel.plus("\n")
                    .plus(chosenItemVehicle?.specification)
            chosenItemVehicle?.let { binding.etServiceEditFragment.setText(it.serviceInfo) }
        }

        binding.etTextTaskToAdd.setText("")

        binding.etServiceEditFragment.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(text: Editable?) { // почему editText хранит предыдущие значения(уже стёртые)
                chosenItemVehicle?.let {
                    it.serviceInfo = text.toString()
                    vehicleViewModel.update(chosenItemVehicle!!)
                }
            }

        })
    }
}