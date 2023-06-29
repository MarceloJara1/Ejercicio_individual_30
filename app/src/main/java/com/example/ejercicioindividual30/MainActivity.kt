package com.example.ejercicioindividual30

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import com.example.ejercicioindividual30.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    private val SHARED_PREFERENCES_NAME = "MyPreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)


        binding.btn1.setOnClickListener {
            guardarValores()
        }

        binding.btn2.setOnClickListener {
            limpiarDatos()
        }

        cargarDatos()

    }

    private fun guardarValores(){

        if (validar()) {
            val value1 = binding.input1.text.toString().toInt()
            val value2 = binding.input2.text.toString()
            val value3 = binding.switch1.isChecked
            val value4 = binding.input3.text.toString().toDouble()

            val values = Values(value1, value2, value3, value4)
            val json = gson.toJson(values)
            sharedPreferences.edit().putString("values", json).apply()

            binding.txt1.text = getString(R.string.valor_almacenado_1_d, value1)
            binding.txt2.text = getString(R.string.texto_almacenado_1_s, value2)
            binding.txt3.text = getString(R.string.valor_decimal_almacenado_1_s, value4)
        }
    }

    private fun limpiarDatos(){
        sharedPreferences.edit().remove("values").apply()

        binding.input1.text?.clear()
        binding.input2.text?.clear()
        binding.switch1.isChecked = false
        binding.input3.text?.clear()

        binding.txt1.text = getString(R.string. valor_almacenado_1_d)
        binding.txt2.text = getString(R.string. texto_almacenado_1_s)
        binding.txt3.text = getString(R.string. valor_decimal_almacenado_1_s)

    }

    private fun cargarDatos(){
        val json = sharedPreferences.getString("values", null)
        val values = gson.fromJson(json, Values::class.java)

        if (values != null){
            binding.input1.setText(values.valor_1.toString())
            binding.input2.setText(values.valor_2)
            binding.switch1.isChecked = values.valor_3
            binding.input3.setText(values.valor_4.toString())

            binding.txt1.text = getString(R.string.valor_almacenado_1_d, values.valor_1)
            binding.txt2.text = getString(R.string.texto_almacenado_1_s, values.valor_2)
            binding.txt3.text = getString(R.string.valor_decimal_almacenado_1_s, values.valor_4)
        }
    }

    private fun validar():Boolean{

        val inputValue1 = binding.input1.text.toString()
        val inputValue2 = binding.input2.text.toString()
        val inputValue3 = binding.input3.text.toString()

        if (inputValue1.isEmpty() || !inputValue1.isDigitsOnly()){
        showSnackbar("Ingrese un valor ENTERO valido para el Valor 1")
            return false
        }

        if (inputValue2.isEmpty()){
            showSnackbar("Ingrese un valor valido para el Valor 2")
            return false
        }
        if (inputValue3.isEmpty() || inputValue3.toDoubleOrNull() == null){
            showSnackbar("Ingrese un valor decimal valido para el Valor 3")
            return false
        }
        return true
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.mainActivity, message, Snackbar.LENGTH_SHORT).show()
    }





}