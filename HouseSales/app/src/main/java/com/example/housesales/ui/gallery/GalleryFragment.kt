package com.example.housesales.ui.gallery

import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.housesales.MainActivity
import com.example.housesales.R
import com.example.housesales.databinding.FragmentGalleryBinding


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val db = MainActivity.DBHelper(context, null)
        val cursor = db.getRecords("bought_houses")

        val inflater = LayoutInflater.from(context)
        val main = root.findViewById(R.id.main) as LinearLayout
        // moving the cursor to first position and
        // appending value in the text view
        cursor!!.moveToFirst()
        if(cursor.count > 0) {
            val cursor2 = db.getHouseById(cursor.getInt(cursor.getColumnIndex(MainActivity.DBHelper.HOUSE_ID_COL)))
            cursor2!!.moveToFirst()
            var new_view = inflater.inflate(R.layout.example,null)
            new_view.findViewById<TextView>(R.id.house_title).text = cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.TITLE_COl))
            new_view.findViewById<TextView>(R.id.house_price).text = cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.PRICE_COL))
            new_view.findViewById<ImageView>(R.id.house_image).setImageURI(Uri.parse(cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.IMG_COL))))
            var ind = cursor2.getInt(cursor2.getColumnIndex(MainActivity.DBHelper.ID_COL))
            var found_button = new_view.findViewById<Button>(R.id.house_buy)
            found_button.setText("Удалить")
            found_button.setBackgroundColor(Color.RED)
            found_button.setTextColor(Color.WHITE)
            found_button.setOnClickListener {
                db.sellHouse(ind)
                main.removeView(new_view)
            }
            main.addView(new_view)
            // moving our cursor to next
            // position and appending values
            while(cursor.moveToNext()){
                val cursor2 = db.getHouseById(cursor.getInt(cursor.getColumnIndex(MainActivity.DBHelper.HOUSE_ID_COL)))
                cursor2!!.moveToFirst()
                var new_view = inflater.inflate(R.layout.example,null)
                new_view.findViewById<TextView>(R.id.house_title).text = cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.TITLE_COl))
                new_view.findViewById<TextView>(R.id.house_price).text = cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.PRICE_COL))
                new_view.findViewById<ImageView>(R.id.house_image).setImageURI(Uri.parse(cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.IMG_COL))))
                var ind = cursor2.getInt(cursor2.getColumnIndex(MainActivity.DBHelper.ID_COL))
                var found_button = new_view.findViewById<Button>(R.id.house_buy)
                found_button.setText("Удалить")
                found_button.setBackgroundColor(Color.RED)
                found_button.setTextColor(Color.WHITE)
                found_button.setOnClickListener {
                    db.sellHouse(ind)
                    main.removeView(new_view)
                }
                main.addView(new_view)
            }
        }
        // at last we close our cursor
        cursor.close()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}