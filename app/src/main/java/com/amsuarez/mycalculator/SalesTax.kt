package com.amsuarez.mycalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_sales_tax.*


class SalesTax : AppCompatActivity() {
    fun EditText.changed() {
        this.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if(totalCost.text.toString() != "" && salesTax.text.toString() != ""){
                    //calculations
                    val totTax = "%.2f".format(totalCost.text.toString().toFloat()*(salesTax.text.toString().toFloat()/100))
                    val totalCoast = "%.2f".format(totTax.toString().toFloat()+totalCost.text.toString().toFloat())

                    //sets the results
                    result.text = ("Total Tax: $$totTax\n" + "Total Cost/Price: $$totalCoast")
                }
                else{
                    //sets the results in case either input is empty
                    result.text = ""
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_tax)

        //change triggers
        totalCost.changed()
        salesTax.changed()

    }
}
