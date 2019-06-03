package com.f.iso8583.screens.field

import android.content.Context
import android.os.Build
import android.view.View
import com.f.iso8583.R
import com.f.iso8583.list.BaseItem
import com.f.iso8583.room.model.Field
import kotlinx.android.synthetic.main.item_field.view.*

class FieldItem(var context: Context, var data: Field?, var typeName: String?, var callback: Callback) :
    BaseItem<Any>(context, data),
    View.OnClickListener {

    interface Callback {
        fun onTapEditItem(data: Field?)
        fun onTapDeleteItem(data: Field?)
    }

    override fun onInitLayout(): Int = R.layout.item_field

    override fun onBindView(view: View?) {
        view?.mTextViewNumberValue?.text = data?.number.toString()
        view?.mTextViewNameValue?.text = data?.name
        view?.mTextViewLengthValue?.text = data?.length
        view?.mTextViewTypeValue?.text = typeName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (typeName) {
                "ans" -> {
                    view?.mConstraintLayoutGroup?.setBackgroundColor(context.getColor(R.color.colorANS))
                }
            }
        }

        view?.mImageViewEdit?.setOnClickListener(this)
        view?.mImageViewDelete?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mImageViewEdit -> {
                callback.onTapEditItem(data)
            }

            R.id.mImageViewDelete -> {
                callback.onTapDeleteItem(data)
            }
        }
    }
}