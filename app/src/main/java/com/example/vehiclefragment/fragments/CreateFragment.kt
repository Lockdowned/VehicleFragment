package com.example.vehiclefragment.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toDrawable
import com.example.vehiclefragment.MainActivity
import com.example.vehiclefragment.R
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.databinding.FragmentCreateBinding
import com.example.vehiclefragment.interfaces.IVehicleCreateListener

class CreateFragment : Fragment(R.layout.fragment_create) {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var localContext: Context

    private val SELECT_IMAGE_CLICK = 1

    private lateinit var newVehicle: VehicleItem

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {

            imageCreateNewVehicle.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, SELECT_IMAGE_CLICK)
            }

            btnSaveOnCreateAct.setOnClickListener {
                if ((editTextBrand.text.toString().isEmpty())
                        || (editTextModel.text.toString().isEmpty())
                        || (editTextYearRel.text.toString().isEmpty())) {
                    Toast.makeText(localContext, "Please fill in the lines marked *",
                            Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                val checkedRadioButtonEngineId = radioGroupEngine.checkedRadioButtonId
                val engine: RadioButton?  = it.findViewById(checkedRadioButtonEngineId)
                val checkedRadioButtonGearId = radioGroupGear.checkedRadioButtonId
                val gear: RadioButton? = it.findViewById(checkedRadioButtonGearId)

                val textBrandAndModel = "${editTextBrand.text.toString()} " +
                        "${editTextModel.text.toString()} ${editTextYearRel.text.toString()}"

                newVehicle = VehicleItem(AppCompatResources.getDrawable(localContext,
                    R.drawable.default_car), textBrandAndModel, "", "")
                Log.d(newVehicle.toString(), "MyLogNewVeh")
                newVehicle.let {
                    (localContext as IVehicleCreateListener).deliverCreatedVehicle(newVehicle)
                }

            }

        }


    }
}