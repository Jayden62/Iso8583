package com.f.iso8583.common

import android.content.Context
import android.view.View
import com.f.iso8583.R
import com.f.iso8583.list.BaseItem

class EmptyItem(context : Context) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_empty

    override fun onBindView(view: View?) {
    }
}