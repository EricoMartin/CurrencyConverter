package com.basebox.ratexchange.ui.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.basebox.ratexchange.R
import com.basebox.ratexchange.databinding.DropDownItemBinding
import com.google.android.material.imageview.ShapeableImageView
import java.util.*


internal class SpinnerAdapter(context: Context, countries: Array<String>) :
    ArrayAdapter<String?>(context, R.layout.drop_down_item, countries) {
    var countriesList: Array<String> = countries

    var thisContext = context

    // Override these methods and instead return our custom view (with image and text)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun getDropDownView(
        position: Int,
        @Nullable convertView: View?,
        parent: ViewGroup
    ): View {
        return getCustomView(position, convertView, parent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    // Function to return our custom View (View with an image and text)
    @RequiresApi(Build.VERSION_CODES.M)
    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding: DropDownItemBinding
        var row = convertView
        if (row == null) {
            val inflater =
                thisContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            binding = DropDownItemBinding.inflate(inflater, parent, false)
            row = binding.root
        } else {
            binding = DropDownItemBinding.bind(row)
        }

        // Image and TextViews
        val country: TextView = row.findViewById(binding.textView2.id)
        val flag: ShapeableImageView = row.findViewById(binding.img.id)

        // Get flag image from drawables folder
        val res: Resources = thisContext.resources
        val drawableName = countriesList[position].lowercase(Locale.getDefault())
        var resId = 0
        if (drawableName == "try")
            resId = res.getIdentifier("tr", "drawable", thisContext.packageName)
        else
            resId = res.getIdentifier(drawableName, "drawable", thisContext.packageName)

        Log.d("SpinnerAdapterTag", "Message = $resId")
        val drawable: Drawable? =
            ResourcesCompat.getDrawable(res, resId, this.dropDownViewTheme)

        //Set state abbreviation and country flag
        country.text = countriesList[position]
        flag.setImageDrawable(drawable)
        return row
    }

    // Constructor accepts Context (from MainActivity) and a list of country abbreviations
    init {
        thisContext = context
        countriesList = countries
    }
}