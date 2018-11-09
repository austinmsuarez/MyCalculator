package com.amsuarez.mycalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_gpacalc.*
import kotlinx.android.synthetic.main.gparow.view.*


data class GpaEntry(var  courseName: String, var grade: String, var credit: String)
val GpaEntries = mutableListOf<GpaEntry>()

class GPACalc : AppCompatActivity() {

    var selectedGrade:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        //constructs activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpacalc)

        // sets a variable to the custom Array adapter
        val adapter  = object:  ArrayAdapter<GpaEntry>(this,R.layout.gparow,R.id.gradeTv,GpaEntries) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
               val item = super.getView(position, convertView, parent)

                //sets adapter items to the ones input
                item.courseNameTV.text = GpaEntries[position].courseName
                item.gradeTv.text = GpaEntries[position].grade
                item.creditsTV.text = GpaEntries[position].credit

               return item
            }
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(this, R.array.gradeOptions, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            gradeOps.adapter = adapter
        }
        // sets the list view to the adapter
        listView.adapter = adapter


        //sets grade based on the one selected by user through spinner
        gradeOps.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedGrade = "A"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(gradeOps.selectedItem.toString()) {
                    "A" ->{selectedGrade = "A"}
                    "B" ->{selectedGrade = "B"}
                    "C" ->{selectedGrade = "C"}
                    "D" ->{selectedGrade = "D"}
                    "F" ->{selectedGrade = "F"}
                }
            }}

        //allows you to add rows dynamically
        addClassB.setOnClickListener{addCourse(adapter)}
        //allows you to delete a class
        deleteClassB.setOnClickListener { deleteLastCourse(adapter) }
    }

    override fun onDestroy() {
        super.onDestroy()
        // deletes all gpa entries when the activity is closed through the back button
        GpaEntries.clear()
    }

    /*******************************************************************************************************************
     * Function:    addCourse
     * PARAMS:      ArrayAdapter
     * Use:         adds a course to course history
     ******************************************************************************************************************/
    fun addCourse(adapter:ArrayAdapter<GpaEntry>) {
        //Adds course to Course history and updated GPA
        if(courseNTV.text.toString() != "" && creditsTV.text.toString() != "") {
            GpaEntries.add(GpaEntry(courseNTV.text.toString(), selectedGrade, creditsTV.text.toString()))
            adapter.notifyDataSetChanged()
            updateGPA()
        }
        //Shows warning for insufficient info
        else{
            warning.text = "need to fill out both course name and credit amount"
        }
    }

    /*******************************************************************************************************************
     * Function:    deleteLastCourse
     * PARAMS:      ArrayAdapter
     * Use:         deletes a course from course history
     ******************************************************************************************************************/
    fun deleteLastCourse(adapter: ArrayAdapter<GpaEntry>){
        // deletes a course if there is more than 0 in history
        if(GpaEntries.size > 0) {
            GpaEntries.removeAt(GpaEntries.size - 1)
            adapter.notifyDataSetChanged()
            updateGPA()
        }
        //if the list is empty shows warning
        else{
            warning.text = "Cannot delete from empty list!"
        }
    }

    /*******************************************************************************************************************
     * Function:    updateGPA
     * PARAMS:      ArrayAdapter
     * Use:         updates GPA
     ******************************************************************************************************************/
    fun updateGPA(){
        var grades:Float = 0.0.toFloat()
        var credits:Float = 0.0.toFloat()
        var gradePts: Float = 0.0.toFloat()

        //cycles through all course history and calculates GPA
        for(i in 0..(GpaEntries.size-1))
        {
            when(GpaEntries[i].grade){
                "A"-> grades = 4.0.toFloat()
                "B" ->grades = 3.0.toFloat()
                "C" ->grades = 2.0.toFloat()
                "D" ->grades = 1.0.toFloat()
                "F" ->grades = 0.0.toFloat()
            }
            credits +=  GpaEntries[i].credit.toString().toFloat()
            gradePts += GpaEntries[i].credit.toString().toFloat()*grades.toFloat()
        }
        // Shows GPA if there are course in history
        if(GpaEntries.size != 0) {
            gpaTV.text = "%.2f".format((gradePts) / credits)
        }
        else{
            gpaTV.text = ""
        }
    }

}

