package com.amsuarez.mycalculator

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


data class menuOption(val buttonText:String)

val calcMenu = arrayOf(
    menuOption("Tips"),
    menuOption("Sales Tax"),
    menuOption("GPA"),
    menuOption("BMI")

)

class listAdapter(context: Context) : ArrayAdapter<menuOption>(context, android.R.layout.simple_list_item_1, android.R.id.text1, calcMenu ) {
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val row = super.getView(position, view, parent)
        val text1 = row.findViewById<TextView>(android.R.id.text1)
        text1.text = calcMenu[position].buttonText
        text1.setPadding(0,30,0,30)
        text1.textSize = 24F
        return row
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = listAdapter(this)

        listView.adapter = adapter

        listView.setOnItemClickListener { _ , _ , position, _->
            when (position) {
                0-> {val intent = Intent(this, Tips::class.java)
                    startActivity(intent)}
                1-> {val intent = Intent(this, SalesTax::class.java)
                    startActivity(intent)}
                2-> {val intent = Intent(this, GPACalc::class.java)
                    startActivity(intent)}
                3-> {val intent = Intent(this, BMI::class.java)
                    startActivity(intent)
                }
                else->{}
            }
        }
    }
}
