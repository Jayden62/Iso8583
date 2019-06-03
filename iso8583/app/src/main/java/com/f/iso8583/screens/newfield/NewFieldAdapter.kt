package com.f.iso8583.screens.newfield

import android.content.Context
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.f.iso8583.R
import com.f.iso8583.room.model.Type

class NewFieldAdapter(context: Context, mTypes: MutableList<Type>) : ArrayAdapter<Type>(context, 0, mTypes) {
    private var data: MutableList<Type> = mTypes

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return customView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return customView(position, convertView, parent)
    }

    private fun customView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = convertView
        view = layoutInflater.inflate(R.layout.item_type, parent, false)
        val mTextViewName = view.findViewById(R.id.mTextViewName) as TextView
        mTextViewName.text = data[position].typeName
        return view
    }
}