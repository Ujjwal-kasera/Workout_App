package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    var bmiBinding : ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bmiBinding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(bmiBinding?.root)

        /** We are setting supportActionBar to toolbarBMI
         * so we can access toolbar with supportActionBar name*/
        setSupportActionBar(bmiBinding?.toolbarBMI)
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        bmiBinding?.toolbarBMI?.setNavigationOnClickListener{
            onBackPressed()
        }

        bmiBinding?.btnCalculateBMI?.setOnClickListener {
            if(validateMetricUnits()){
                val weightValue : Float = bmiBinding?.etMetricUnitWeight?.text.toString().toFloat()
                val heightValue : Float = bmiBinding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val bmi = weightValue / (heightValue*heightValue)

                displayBMIResult(bmi)
            }else{
                Toast.makeText(this@BmiActivity,
                    "Please Enter Valid Input",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun displayBMIResult(bmi:Float){

        val bmiLabel : String
        val bmiDescription : String

        if(bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <=0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <=0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are perfect!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <=0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You need to take better care of yourself! Workout more!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <=0) {
            bmiLabel = "Moderately Obese"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Severely Obese"
            bmiDescription = "OMG! You are in a dangerous condition! Act Now!"
        }else {
            bmiLabel = "Very Severely Obese"
            bmiDescription = "OMG! You are in a very dangerous condition! Act Now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2,RoundingMode.HALF_EVEN).toString()

        bmiBinding?.tvBMIValue?.text = bmiValue
        bmiBinding?.tvBMIType?.text = bmiLabel
        bmiBinding?.tvBMIDescription?.text = bmiDescription
        bmiBinding?.llDisplayBMIResult?.visibility = View.VISIBLE
    }

    private fun validateMetricUnits():Boolean{
        
        var isValid=true
        if(bmiBinding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid=false
        }else if(bmiBinding?.etMetricUnitHeight?.text.toString().isEmpty())
            isValid=false

        return isValid
    }
}