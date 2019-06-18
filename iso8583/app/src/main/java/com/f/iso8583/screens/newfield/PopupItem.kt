package com.f.iso8583.screens.newfield

import android.content.Context
import android.view.View
import com.f.iso8583.R
import com.f.iso8583.list.BaseItem
import kotlinx.android.synthetic.main.item_popup_window.view.*

class PopupItem(context: Context, var category: String?, var callback: Callback) : BaseItem<Any>(context) {

    interface Callback {
        fun onTapItem(text: String?)
    }

    override fun onInitLayout(): Int = R.layout.item_popup_window

    override fun onBindView(view: View?) {

        view?.mTextViewCategory?.text = category

        view?.mTextViewCategory?.setOnClickListener {
            callback.onTapItem(category)
        }
    }
}