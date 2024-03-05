package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    class Employee  {
        var empId: Int = 0
        lateinit var name: String
        lateinit var ssn: String
        var salary: Double = 0.0

        constructor (empId: Int, name: String, ssn: String, salary: Double) {
            this.empId = empId
            this.name = name
            this.ssn = ssn
            this.salary = salary
        }
    }

    fun main() {
        var employee = Employee(0, "test", "test", 56236.5)
    }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var is_switched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

     public fun switch_view(view: View) {
         is_switched = !is_switched
         if(is_switched)
            setContentView(R.layout.activity_secondary)
         else
            setContentView(R.layout.activity_main)
     }

    public fun convert(view: View) {
        var edit_text = findViewById<EditText>(R.id.editTextNumber)?.getText().toString().toFloat()
        if(!is_switched)
            Toast.makeText(this, "Результат: " + edit_text *  2.205f, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Результат: " + edit_text /  2.205f, Toast.LENGTH_SHORT).show();


    }
}