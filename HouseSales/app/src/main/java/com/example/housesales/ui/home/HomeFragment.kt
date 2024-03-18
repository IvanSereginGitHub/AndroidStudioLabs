package com.example.housesales.ui.home

import android.net.Uri
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
import androidx.lifecycle.ViewModelProvider
import com.example.housesales.MainActivity
import com.example.housesales.R
import com.example.housesales.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val db = MainActivity.DBHelper(context, null)

        val cursor2 = db.getRecords("all_houses")

        val inflater = LayoutInflater.from(context)
        val main = root.findViewById(R.id.main) as LinearLayout
        // moving the cursor to first position and
        // appending value in the text view
        cursor2!!.moveToFirst()
        if(cursor2.count > 0) {
            //Log.d("tag","" + cursor2.getColumnIndex(MainActivity.DBHelper.IMG_COL) + cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.IMG_COL)) + cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.TITLE_COl)))
            var new_view = inflater.inflate(R.layout.example,null)
            new_view.findViewById<TextView>(R.id.house_title).text = cursor2.getString(cursor2.getColumnIndex(
                MainActivity.DBHelper.TITLE_COl))
            new_view.findViewById<TextView>(R.id.house_price).text = cursor2.getString(cursor2.getColumnIndex(
                MainActivity.DBHelper.PRICE_COL))
            new_view.findViewById<ImageView>(R.id.house_image).setImageURI(Uri.parse(cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.IMG_COL))))
            var found_id = cursor2.getInt(cursor2.getColumnIndex(
                MainActivity.DBHelper.ID_COL))
            new_view.findViewById<Button>(R.id.house_buy).setOnClickListener {
                db.buyHouse(found_id)
            }
            main.addView(new_view)
            // moving our cursor to next
            // position and appending values
            while(cursor2.moveToNext()){
                var new_view = inflater.inflate(R.layout.example,null)
                new_view.findViewById<TextView>(R.id.house_title).text = cursor2.getString(cursor2.getColumnIndex(
                    MainActivity.DBHelper.TITLE_COl))
                new_view.findViewById<TextView>(R.id.house_price).text = cursor2.getString(cursor2.getColumnIndex(
                    MainActivity.DBHelper.PRICE_COL))
                new_view.findViewById<ImageView>(R.id.house_image).setImageURI(Uri.parse(cursor2.getString(cursor2.getColumnIndex(MainActivity.DBHelper.IMG_COL))))
                var found_id = cursor2.getInt(cursor2.getColumnIndex(
                    MainActivity.DBHelper.ID_COL))
                new_view.findViewById<Button>(R.id.house_buy).setOnClickListener {
                    db.buyHouse(found_id)
                }
                main.addView(new_view)
            }
        }
        // at last we close our cursor
        cursor2.close()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}