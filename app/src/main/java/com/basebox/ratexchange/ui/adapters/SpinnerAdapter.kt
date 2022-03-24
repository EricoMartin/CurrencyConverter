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
import com.basebox.ratexchange.databinding.DropDownItemBinding
import com.google.android.material.imageview.ShapeableImageView
import java.util.*


internal class SpinnerAdapter(context: Context, private val layout: Int, states: Array<String>) :
    ArrayAdapter<String?>(context, layout, states) {
    var statesList: Array<String> = states

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

        var binding: DropDownItemBinding
        var row = convertView
        if (row == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            binding = DropDownItemBinding.inflate(inflater, parent, false)
            var row = binding.root

            // Image and TextViews
            val state: TextView = row.findViewById(binding.textView2.id)
            val flag: ShapeableImageView = row.findViewById(binding.img.id)

            // Get flag image from drawables folder
            val res: Resources = context.resources
            val drawableName = statesList[position].lowercase(Locale.getDefault()) // tx
            val resId = res.getIdentifier(drawableName, "drawable", context.packageName)
            Log.d("SpinnerAdapterTag", "Message = $resId")
            val drawable: Drawable? =
                ResourcesCompat.getDrawable(res, resId, this.dropDownViewTheme)

            //Set state abbreviation and state flag
            state.text = statesList[position]
            flag.background = drawable
        }
        return row!!
    }

    // Constructor accepts Context (from MainActivity) and a list of state abbreviations
    init {
        statesList = states
    }
}