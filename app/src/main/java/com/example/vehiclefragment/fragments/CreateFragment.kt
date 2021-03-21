package com.example.vehiclefragment.fragments

import android.annotation.SuppressLint
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
import androidx.appcompat.content.res.AppCompatResources
import com.example.vehiclefragment.R
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.databinding.FragmentCreateBinding
import com.example.vehiclefragment.helperObject.ConverterUriToDrawable
import com.example.vehiclefragment.interfaces.IVehicleCreatedVehicleListener

class CreateFragment : Fragment() {

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

        newVehicle = VehicleItem(AppCompatResources.getDrawable(localContext,
                R.drawable.default_car), "textBrandAndModel", "", "", null)

        binding.run {

            imageCreateNewVehicle.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
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
                setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        text = if (text.equals("kW")) {
                            "Watt"
                        } else {
                            "kW"
                        }
                    }
                })

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

                seekBarPower.addOnChangeListener { slider, value, fromUser ->
                    tvPowerDisplay.text = "Power: ${value.toInt()}"
                }

                radioGroupEngine.setOnCheckedChangeListener { group, checkedId ->

                }

                radioGroupEngine.setOnCheckedChangeListener { group, checkedId ->
                    if (checkedId == radioButtonElectricEngine.id){
                        etEngineMl.text.clear()
                        etEngineMl.isEnabled = false
                    } else {
                        etEngineMl.isEnabled = true
                        tvPowerDisplay.text = "Power"
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

                    val textBrandAndModel = "${editTextBrand.text.toString()} " +
                            "${editTextModel.text.toString()} ${editTextYearRel.text.toString()}"
                    newVehicle.brandAndModel = textBrandAndModel

                    val checkedRadioButtonEngineId = radioGroupEngine.checkedRadioButtonId
                    val engine: RadioButton? = radioGroupEngine.findViewById(checkedRadioButtonEngineId)
                    val checkedRadioButtonGearId = radioGroupGear.checkedRadioButtonId
                    val gear: RadioButton? = radioGroupGear.findViewById(checkedRadioButtonGearId)
                    val specification = (if (engine?.let { true } == true) "${engine.text} " else "") +
                            (if (gear?.let { engine != radioButtonElectricEngine} == true)
                                "${gear.text} " else "") +
                            (if (etEngineMl.isEnabled)
                                "capacity: ${etEngineMl.text}${spinnerEngineMeasure.selectedItem} "
                            else "") +
                            (if (!tvPowerDisplay.text.equals("Power") &&
                                    engine?.let { engine == radioButtonElectricEngine } == true)
                                "${tvPowerDisplay.text}${chTexPowerMeasure.text} "
                            else "")

                    newVehicle.specification = specification



                    newVehicle.let {
                        (localContext as IVehicleCreatedVehicleListener).deliverVehicle(newVehicle)
                    }

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_IMAGE_CLICK) {
            data?.data.run {
                binding.imageCreateNewVehicle.setImageURI(this)
                newVehicle.img = ConverterUriToDrawable.uriToDrawable(this.toString(), localContext)
                newVehicle.uriString = this.toString()
            }

        }
    }





}