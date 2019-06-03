package com.f.iso8583.screens.parser

import android.content.Context
import android.view.View
import com.f.iso8583.R
import com.f.iso8583.list.BaseItem
import kotlinx.android.synthetic.main.item_parser.view.*

class ParserItem(context: Context, var field: String?, var value: String?) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_parser

    override fun onBindView(view: View?) {
        view?.mTextViewField?.text = field
        view?.mTextViewFieldValue?.text = value
    }
}