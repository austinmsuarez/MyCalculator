package com.amsuarez.mycalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_tips.*

class Tips : AppCompatActivity() {

    //creates a trigger when a editText changes
    fun EditText.changed() {
        this.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // checks to see if fields for bill and tip have input
                if(billAmt.text.toString() != "" && tipAmt.text.toString() != ""){

                    //calculations
                    val total = "%.2f".format(billAmt.text.toString().toFloat() * (1 + (tipAmt.text.toString().toFloat())/100))
                    val tip = "%.2f".format(billAmt.text.toString().toFloat() * ((tipAmt.text.toString().toFloat())/100))

                    //Checks to see if the bill needs to be split
                    if(!splitBill.isChecked){
                        //sets result to tip and total
                        resultTotal.text = ("Tip Amount:    \t$$tip \n" +
                                            "Total Price:   \t$$total")
                    }
                    else{

                        //checks to see how many people are going to split the bill
                        if(peopleAmt.text.toString() != ""){
                            // calculation
                            val totalPerPerson  = "%.2f".format(total.toFloat()/peopleAmt.text.toString().toInt())
                            val tipPerPerson = "%.2f".format(tip.toFloat()/peopleAmt.text.toString().toInt())

                            //sets results to tip and total per person
                            perPerson.text = ("Per Person\n" +
                                              "Tip Amount:    \t$$tipPerPerson\n" +
                                              "Total Price:   \t$$totalPerPerson")
                        }
                        // if there is no person amt then it clears textView
                        else{
                            perPerson.text =""
                        }
                    }
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)
        //triggers for textView changes
        billAmt.changed()
        tipAmt.changed()
        peopleAmt.changed()

    }
}
