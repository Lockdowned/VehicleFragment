package com.example.vehiclefragment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.databinding.ActivityMainBinding
import com.example.vehiclefragment.fragments.CreateFragment
import com.example.vehiclefragment.fragments.EditFragment
import com.example.vehiclefragment.fragments.ListFragment
import com.example.vehiclefragment.helperObject.InitHelp
import com.example.vehiclefragment.interfaces.IVehicleToEditListener
import com.example.vehiclefragment.viewmodels.VehicleViewModel

class MainActivity : AppCompatActivity(), IVehicleToEditListener{

    private lateinit var listFragment: ListFragment
    private lateinit var createFragment: CreateFragment
    private lateinit var editFragment: EditFragment

    private lateinit var binding: ActivityMainBinding

    private var itemVehicleToEdit: VehicleItem? = null

    private val vehicleViewModel: VehicleViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vehicleViewModel.allVehicle.value = InitHelp.initialize(this)

        listFragment = ListFragment()
        createFragment = CreateFragment()
        editFragment = EditFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.mainFragment, createFragment)
            add(R.id.mainFragment, editFragment)
            add(R.id.mainFragment, listFragment)
            commit()
        }
        setCurrentFragment(listFragment)



        binding.mainMenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miList -> setCurrentFragment(listFragment)
                R.id.miCreate -> {
                    createFragment = CreateFragment()
                    setCurrentFragment(createFragment)
                }
                R.id.miEdit -> {
                    itemVehicleToEdit?.let {
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

    override fun toEdit(item: VehicleItem) {
        itemVehicleToEdit = item
        vehicleViewModel.select(item)
        binding.mainMenu.selectedItemId = R.id.miEdit
    }

    override fun toList() {
        binding.mainMenu.selectedItemId = R.id.miList
    }

}