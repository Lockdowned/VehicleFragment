package com.example.vehiclefragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.databinding.ActivityMainBinding
import com.example.vehiclefragment.fragments.CreateFragment
import com.example.vehiclefragment.fragments.EditFragment
import com.example.vehiclefragment.fragments.ListFragment
import com.example.vehiclefragment.interfaces.IVehicleCreateListener
import com.example.vehiclefragment.interfaces.IVehicleEditListener

class MainActivity : AppCompatActivity(), IVehicleCreateListener, IVehicleEditListener {

    private lateinit var listFragment: ListFragment
    private lateinit var createFragment: CreateFragment
    private lateinit var editFragment: EditFragment

    private lateinit var binding: ActivityMainBinding


    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



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
                R.id.miCreate -> setCurrentFragment(createFragment)
                R.id.miEdit -> setCurrentFragment(editFragment)
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

    override fun deliverCreatedVehicle(vehicleItem: VehicleItem) {
        binding.mainMenu.selectedItemId = R.id.miList
        listFragment.addNewVehicleItem(vehicleItem)
    }

    override fun itemToEdit(item: VehicleItem) {
        editFragment.fillEditView(item)
        binding.mainMenu.selectedItemId = R.id.miEdit
    }


}