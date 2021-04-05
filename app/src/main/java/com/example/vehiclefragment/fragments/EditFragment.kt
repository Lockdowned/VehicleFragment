package com.example.vehiclefragment.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehiclefragment.MainActivity
import com.example.vehiclefragment.R
import com.example.vehiclefragment.adaptor.TaskListAdaptor
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.databinding.FragmentEditBinding
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.interfaces.IFragmentCommunication
import com.example.vehiclefragment.viewmodels.TaskViewModel
import com.example.vehiclefragment.viewmodels.VehicleViewModel

class EditFragment(
        private val vehicleViewModel: VehicleViewModel,
        private val taskViewModel: TaskViewModel)
    : Fragment(R.layout.fragment_edit) {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private var chosenItemVehicle: VehicleItem? = null

    lateinit var localContext: IFragmentCommunication

    private var taskListAdaptor: TaskListAdaptor? = null

    var fromEditChangeString: String? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context as IFragmentCommunication
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null){
            _binding = FragmentEditBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chosenItemVehicle = vehicleViewModel.getSelected()

//        binding.imageViewServicePage.setImageDrawable(chosenItemVehicle?.img)
        binding.tvBrandTextEditFragment.text = chosenItemVehicle?.brandAndModel
            .plus(chosenItemVehicle?.specification)



        chosenItemVehicle?.let { taskViewModel.setTasks(it.id!!) }

        taskViewModel.allTasks?.observe(viewLifecycleOwner, {
            Toast.makeText(localContext as MainActivity, it.toString() , Toast.LENGTH_LONG).show()

            taskListAdaptor = TaskListAdaptor(it, taskViewModel)
            binding.rvTaskList.adapter = taskListAdaptor
            binding.rvTaskList.layoutManager = LinearLayoutManager(localContext as MainActivity)

        })



        binding.buttonAddTask.setOnClickListener {
            if (binding.etTextTaskToAdd.text.isNotEmpty()){
                val task = TaskItem(
                        false,
                        binding.etTextTaskToAdd.text.toString(),
                        chosenItemVehicle!!.id
                )
                taskViewModel.insertTask(task)
            }
        }

        chosenItemVehicle?.let {

//            taskListAdaptor = TaskListAdaptor(taskViewModel.getList(1))
            binding.rvTaskList.adapter = taskListAdaptor
            binding.rvTaskList.layoutManager = LinearLayoutManager(localContext as MainActivity)
        }



        //for clear xml etServiceInf
//        binding.etServiceEditFragment.text.clear()

        fromEditChangeString = chosenItemVehicle?.serviceInfo





    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        chosenItemVehicle?.let { binding.etServiceEditFragment.setText(it.serviceInfo) }
        binding.etTextTaskToAdd.setText("")

        binding.etServiceEditFragment.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                fromEditChangeString = text.toString()
            }

        })
    }

}