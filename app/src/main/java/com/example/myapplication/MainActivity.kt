package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.myapplication.databinding.ActivityMainBinding


class Book  {
    var bookId: Int = 0
    var name: String
    var author: String
    var price: Double = 0.0

    constructor (bookId: Int, name: String, author: String, price: Double) {
        this.bookId = bookId
        this.name = name
        this.author = author
        this.price = price
    }
    public fun changeId(_bookId: Int) {
        this.bookId = _bookId
    }
    public fun changeName(_name: String) {
        this.name = _name
    }
    public fun changeAuthor(_author: String) {
        this.author = _author
    }
    public fun changePrice(_price: Double) {
        this.price = _price
    }
    public fun printBook() {
        System.out.println("Книга " + this.name + " (" + bookId + ", " + author + ", " + price + ")")
    }
}

class MainActivity : AppCompatActivity() {

    fun main() {
        var book = Book(0, "Война и Мир", "Какой-то автор", 256.50)
        book.changeAuthor("Лев Николаевич Толстой")
        book.printBook()
    }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var is_switched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    public fun move_car (view: View) {
        var anim = AnimationUtils.loadAnimation(this, R.anim.anim)
        var img = findViewById<ImageView>(R.id.car)
        img.startAnimation(anim)
    }

    public fun open_anim(view: View) {
        setContentView(R.layout.activity_third)
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