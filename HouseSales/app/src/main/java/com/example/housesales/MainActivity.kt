package com.example.housesales

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.housesales.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    class DBHelper(context: Context?, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
        var cont = context
        // below is the method for creating a database by a sqlite query
        override fun onCreate(db: SQLiteDatabase) {
            // below is a sqlite query, where column names
            // along with their data types is given\
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
            var query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " ("
                    + ID_COL + " INTEGER PRIMARY KEY, " +
                    TITLE_COl + " TEXT," +
                    PRICE_COL + " FLOAT" + ", img_title TEXT)")
            var query2 = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY, " +
                    HOUSE_ID_COL + " INTEGER, FOREIGN KEY(" + HOUSE_ID_COL + ") REFERENCES " + TABLE_NAME2 + "(" + ID_COL + "))")

            // we are calling sqlite
            // method for executing our query
            db.execSQL(query)
            db.execSQL(query2)
        }

        override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
            // this method is to check if table already exists
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
            onCreate(db)
        }
        fun checkIfHouseAlreadyBought(house_id: Int): Cursor? {
            val db = this.readableDatabase

            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE house_id = " + house_id, null)
        }
        // This method is for adding data in our database
        fun addHouse(title : String, price : Double, img_path: String){
            Log.d("tag","add " + title + price + img_path)
            // below we are creating
            // a content values variable
            val values = ContentValues()

            // we are inserting our values
            // in the form of key-value pair
            values.put(TITLE_COl, title)
            values.put(PRICE_COL, price)
            values.put(IMG_COL, img_path)
            // here we are creating a
            // writable variable of
            // our database as we want to
            // insert value in our database
            val db = this.writableDatabase

            // all values are inserted into database
            db.insert(TABLE_NAME2, null, values)
            Log.d("tag","tesst is ")
            // at last we are
            // closing our database
            db.close()
        }

        fun clearTable(name: String) {
            val db = this.readableDatabase

            db.execSQL("DELETE FROM " + name)
        }

        fun buyHouse(id: Int) {

            if(checkIfHouseAlreadyBought(id)!!.count >= 1)
                Toast.makeText(cont, "Дом с id " + id + " уже куплен!", Toast.LENGTH_LONG).show()
            // below we are creating
            // a content values variable
            val values = ContentValues()

            // we are inserting our values
            // in the form of key-value pair
            values.put(HOUSE_ID_COL, id)

            // here we are creating a
            // writable variable of
            // our database as we want to
            // insert value in our database
            val db = this.writableDatabase

            // all values are inserted into database
            db.insert(TABLE_NAME, null, values)
            // at last we are
            // closing our database
            db.close()
            Toast.makeText(cont, "Успешно куплен дом с id " + id, Toast.LENGTH_LONG).show()
        }

        // below method is to get
        // all data from our database
        fun getRecords(table: String): Cursor? {

            // here we are creating a readable
            // variable of our database
            // as we want to read value from it
            val db = this.readableDatabase

            // below code returns a cursor to
            // read data from the database
            return db.rawQuery("SELECT * FROM " + table, null)

        }

        fun getHouseById(record_id: Int): Cursor? {

            // here we are creating a readable
            // variable of our database
            // as we want to read value from it
            val db = this.readableDatabase

            // below code returns a cursor to
            // read data from the database
            return db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE id = " + record_id, null)

        }

        companion object{
            // here we have defined variables for our database

            // below is variable for database name
            private val DATABASE_NAME = "houses_db"

            // below is the variable for database version
            private val DATABASE_VERSION = 1

            // below is the variable for table name
            val TABLE_NAME = "bought_houses"

            // below is the variable for table name
            val TABLE_NAME2 = "all_houses"

            // below is the variable for id column
            val ID_COL = "id"

            // below is the variable for name column
            val TITLE_COl = "title"

            // below is the variable for age column
            val PRICE_COL = "price"

            val HOUSE_ID_COL = "house_id"
            val IMG_COL = "img_title"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DBHelper(this, null)
        db.clearTable("all_houses")
        db.clearTable("bought_houses")
        db.addHouse("Дом в Сан-Франциско", 719900.2, "a_1")
        db.addHouse("Дом в Италии", 421420.74, "a_2")
        db.addHouse("Дом в Австралии", 450000.3, "a_3")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}