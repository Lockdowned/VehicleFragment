package com.example.vehiclefragment.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.vehiclefragment.R
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.databinding.FragmentCreateBinding
import com.example.vehiclefragment.db.entities.ImagesItem
import com.example.vehiclefragment.interfaces.IFragmentCommunication
import com.example.vehiclefragment.viewmodels.VehicleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val SELECT_IMAGE_CLICK = 1

class CreateFragment(private val vehicleViewModel: VehicleViewModel) : Fragment() {

    private lateinit var binding: FragmentCreateBinding

    private lateinit var localContext: Context

    private lateinit var newVehicle: VehicleItem

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newVehicle = VehicleItem("textBrandAndModel", "", "")

        binding.run {

            imageCreateNewVehicle.setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "image/*"
                startActivityForResult(intent, SELECT_IMAGE_CLICK)
            }

            seekBarEngine.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    etEngineMl.setText(progress.toString())
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })

            chTexPowerMeasure.run {
                setOnClickListener {
                    text = if (text.equals("kW")) {
                        "Watt"
                    } else {
                        "kW"
                    }
                }

                ArrayAdapter.createFromResource(localContext, R.array.engine_measure,
                        android.R.layout.simple_spinner_item).also { spinnerAdaptor ->
                    spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_item)
                    spinnerEngineMeasure.adapter = spinnerAdaptor
                }
//                spinnerEngineMeasure.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    }
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                    }
//                }

                seekBarPower.addOnChangeListener { _, value, _ ->
                    tvPowerDisplay.text = resources.getString(R.string.power)
                            .plus(": ${value.toInt()}")
                }

                radioGroupEngine.setOnCheckedChangeListener { _, checkedId ->
                    if (checkedId == radioButtonElectricEngine.id){
                        etEngineMl.text.clear()
                        etEngineMl.isEnabled = false
                    } else {
                        etEngineMl.isEnabled = true
                        tvPowerDisplay.text = resources.getString(R.string.power)
                    }
                }

                btnSaveOnCreateAct.setOnClickListener {
                    if ((editTextBrand.text.toString().isEmpty())
                            || (editTextModel.text.toString().isEmpty())
                            || (editTextYearRel.text.toString().isEmpty())) {
                        Toast.makeText(localContext, "Please fill in the lines marked *",
                                Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    val textBrandAndModel = "${editTextBrand.text} " +
                            "${editTextModel.text} ${editTextYearRel.text}"
                    newVehicle.brandAndModel = textBrandAndModel

                    newVehicle.specification = pickDataSpecificXml()

                    newVehicle.let {
                        vehicleViewModel.insertRoom(it)
                        (localContext as IFragmentCommunication).toList()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_IMAGE_CLICK) {
            data?.data.let { imgUri ->
                binding.imageCreateNewVehicle.setImageURI(imgUri)
                val refId = vehicleViewModel.allVehicle.value?.size?.inc() ?: -1
                newVehicle.img = refId
                val newImg = ImagesItem(
                    imgUri.toString(),
                    refId
                )
                vehicleViewModel.insertImg(newImg)
                if (imgUri != null) {
                    vehicleViewModel.insertImgToCloud(refId, imgUri)
                }
            }
        }
    }

    private fun pickDataSpecificXml(): String{
        var specification = ""
        binding.run {
            val checkedRadioButtonEngineId = radioGroupEngine.checkedRadioButtonId
            val engine: RadioButton? = radioGroupEngine.findViewById(checkedRadioButtonEngineId)
            val checkedRadioButtonGearId = radioGroupGear.checkedRadioButtonId
            val gear: RadioButton? = radioGroupGear.findViewById(checkedRadioButtonGearId)
            fun engineEqualElectric() = engine?.let {engine == radioButtonElectricEngine}
            specification =
                    (if (engine != null)
                        "${engine.text} " else "") +
                    (if (gear?.let { engine != radioButtonElectricEngine} == true)
                        "${gear.text} " else "") +
                    (if (etEngineMl.isEnabled && etEngineMl.text.isNotEmpty())
                        "capacity: ${etEngineMl.text}${spinnerEngineMeasure.selectedItem} " else "") +
                    (if (!tvPowerDisplay.text.equals("Power") && engineEqualElectric() == true)
                        "${tvPowerDisplay.text}${chTexPowerMeasure.text} " else "")
        }
        return specification
    }
}