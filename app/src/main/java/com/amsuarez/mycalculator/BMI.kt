package com.amsuarez.mycalculator

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_bmi.*

class BMI : AppCompatActivity() {
    fun EditText.changed() {
        this.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if(inches.visibility == View.VISIBLE){
                    if(weight.text.toString() != "" && heightUser.text.toString() != "" && inches.text.toString() != ""){
                        val heightConvert = (heightUser.text.toString().toDouble()*12)+ inches.text.toString().toDouble()
                        result.text = "%.1f".format((703*weight.text.toString().toInt()/  (heightConvert * heightConvert)))
                        changeTextColor(result.text.toString().toFloat())

                    }
                    else{
                        result.text = null
                    }
                }
                else{
                    if(weight.text.toString() != "" && heightUser.text.toString() != "") {
                        val heightConvert = (heightUser.text.toString().toDouble() * heightUser.text.toString().toDouble())/100
                        result.text ="%.1f".format((weight.text.toString().toInt() / heightConvert)*100)
                        changeTextColor(result.text.toString().toFloat())
                    }
                    else{
                        result.text = null
                    }
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        categories.text = ("BMI Categories: \n" +
                          "Underweight = <18.5\n" +
                          "Normal weight = 18.5–24.9 \n" +
                          "Overweight = 25–29.9 \n" +
                          "Obesity = BMI of 30 or greater\n")

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(this, R.array.unitCategory, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (spinner.selectedItem.toString() == "Metric"){
               resetView("centimeters", "kilograms")
               inches.visibility = View.INVISIBLE
           }
            else{
               resetView("feet", "pounds")
               inches.visibility = View.VISIBLE
           }
        }}

        weight.changed()
        heightUser.changed()
        inches.changed()
    }

    fun changeTextColor(bmi: Float){
        if(bmi < 18.5){
            result.setTextColor( Color.parseColor("#00b229"))
        }
        else if(bmi > 18.5 && bmi <= 24.9){
            result.setTextColor(Color.parseColor("#c1a000"))
        }
        else if(bmi >= 25 && bmi < 29.9){
            result.setTextColor(Color.parseColor("#dd6700"))
        }
        else if(bmi >= 29.9) {
            result.setTextColor(Color.parseColor("#dd0000"))
        }


    }

    fun resetView(unitHeight: String, unitWeight:String){
        result.text = null
        heightUser.text = null
        inches.text = null
        weight.text = null
        weight.hint = unitWeight
        heightUser.hint = unitHeight
    }

}
