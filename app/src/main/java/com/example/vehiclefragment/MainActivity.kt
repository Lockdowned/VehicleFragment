package com.example.vehiclefragment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vehiclefragment.databinding.ActivityMainBinding
import com.example.vehiclefragment.fragments.CreateFragment
import com.example.vehiclefragment.fragments.EditFragment
import com.example.vehiclefragment.fragments.ListFragment
import com.example.vehiclefragment.interfaces.IFragmentCommunication
import com.example.vehiclefragment.viewmodels.TaskViewModel
import com.example.vehiclefragment.viewmodels.TaskViewModelFactory
import com.example.vehiclefragment.viewmodels.VehicleViewModel
import com.example.vehiclefragment.viewmodels.VehicleViewModelFactory

class MainActivity : AppCompatActivity(), IFragmentCommunication{

    private val vehicleViewModel: VehicleViewModel by viewModels{
        VehicleViewModelFactory(
                (application as VehicleApplication).vehicleRoomRepository,
                (application as VehicleApplication).vehicleFirestoreRepository)
    }
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as VehicleApplication).taskRepository)
    }

    private lateinit var listFragment: ListFragment
    private lateinit var createFragment: CreateFragment
    private lateinit var editFragment: EditFragment

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listFragment = ListFragment(vehicleViewModel)
        createFragment = CreateFragment(vehicleViewModel)
        editFragment = EditFragment(taskViewModel, vehicleViewModel)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.mainFragment, createFragment)
            add(R.id.mainFragment, editFragment)
            add(R.id.mainFragment, listFragment)
            commit()
        }
        setCurrentFragment(listFragment)

        binding.mainMenu.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.miList -> {
                    setCurrentFragment(listFragment)
                }
                R.id.miCreate -> {
                    createFragment = CreateFragment(vehicleViewModel)
                    setCurrentFragment(createFragment)
                }
                R.id.miEdit -> {
                    vehicleViewModel.selectedIdVehicle?.let {
                        setCurrentFragment(editFragment)
                        return@setOnNavigationItemSelectedListener true
                    }
                    Toast.makeText(this,
                            "Fellow, need choose vehicle", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, fragment)
            commit()
        }
    }

    override fun toEdit() {
        binding.mainMenu.selectedItemId = R.id.miEdit
    }

    override fun toList() {
        binding.mainMenu.selectedItemId = R.id.miList
    }

}