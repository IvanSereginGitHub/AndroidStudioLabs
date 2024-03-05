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


class SecondaryActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    public fun switch_view(view: View) {
        Toast.makeText(this, "Switching to main activity", Toast.LENGTH_SHORT).show();
        this.
        setContentView(R.layout.activity_main)
    }

    public fun convert(view: View) {
        var edit_text = findViewById<EditText>(R.id.editTextNumber)?.getText().toString().toFloat()
        Toast.makeText(this, "Результат: " + edit_text *  2.205f, Toast.LENGTH_SHORT).show();
    }
}