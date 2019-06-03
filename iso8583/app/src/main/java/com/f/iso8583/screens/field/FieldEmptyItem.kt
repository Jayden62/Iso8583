package com.f.iso8583.screens.field

import android.content.Context
import android.view.View
import com.f.iso8583.R
import com.f.iso8583.list.BaseItem

class FieldEmptyItem(context : Context) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_empty_config

    override fun onBindView(view: View?) {
    }
}