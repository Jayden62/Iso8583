package com.f.iso8583.screens.newfield

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import com.f.iso8583.R
//import com.f.iso8583.room.model.Field
//import com.f.iso8583.room.model.Type
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.f.iso8583.room.model.Field
import com.f.iso8583.room.model.Type
import com.f.iso8583.room.repository.FieldRepository
import com.f.iso8583.room.repository.TypeRepository
import com.f.iso8583.utils.Constant
import com.f.iso8583.utils.Dialog
import kotlinx.android.synthetic.main.activity_new_field.*
import java.util.*
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.PopupWindow
import com.f.iso8583.custom.DividerItemDecorationMargin
import com.f.iso8583.list.BaseAdapter
import com.f.iso8583.model.Selected
import com.f.iso8583.utils.Dimension
import kotlinx.android.synthetic.main.layout_popup_window.view.*

class NewFieldActivity : AppCompatActivity(), View.OnClickListener, TextWatcher, PopupItem.Callback {

    private var types: MutableList<String> = mutableListOf("n", "z", "an", "ans", "b")

    private var typeRepository: TypeRepository? = null

    private var fieldRepository: FieldRepository? = null

    private var value: Type? = null

    private var field: Field? = null

    private var mAdapterPopup: BaseAdapter<Any>? = null

    private var mPopupWindow: PopupWindow? = null

    private var isShowPopup: Boolean = false

    private var mSelected: Selected? = null

    private var TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_field)
        mButtonSave.setOnClickListener(this)
        mButtonClose.setOnClickListener(this)
        mButton.setOnClickListener(this)
        typeRepository = TypeRepository(this)
        fieldRepository = FieldRepository(this)
        mEditTextFieldNumber?.addTextChangedListener(this)

        initData()

        if (intent.hasExtra(Constant.Data.DATA)) {
            field = intent.getParcelableExtra(Constant.Data.DATA)

            mEditTextFieldNumber.setText(field?.number)
            mEditTextFieldName.setText(field?.name)
            mEditTextLength.setText(field?.length)
        }

        initPopupWindow()

    }

    private fun initData() {
        val mProgressDialog = Dialog.showLoadingDialog(this, getString(R.string.load_data))
        typeRepository?.getAllTypes()?.observe(this, Observer<MutableList<Type>> { data ->
            if (data.isNullOrEmpty()) {
                val typesObj: MutableList<Type> = mutableListOf()
                for (typeName in types) {
                    val type = Type(UUID.randomUUID().toString(), typeName)
                    data?.add(type)
                    typesObj.add(type)
                }
                typeRepository?.addTypes(typesObj)
            }
            Handler().postDelayed({ mProgressDialog?.dismiss() }, 1500)
            val mSpinnerAdapter = data?.let { NewFieldAdapter(this, it) }
            mSpinner.adapter = mSpinnerAdapter
        })

        mSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                value = if (position == 0) {
                    mSpinner?.selectedItem as Type?
                } else {
                    mSpinner?.selectedItem as Type?
                }
                print(value)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun afterTextChanged(s: Editable?) {
        Log.d(TAG, "afterTextChanged")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Log.d(TAG, "beforeTextChanged")

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.toString() == "2" || s.toString() == "54" || s.toString() == "55" || s.toString() == "45" || s.toString() == "48"
            || s.toString() == "60" || s.toString() == "61" || s.toString() == "62" || s.toString() == "63" || s.toString() == "35"
        ) {
            mEditTextLength.setText("0")
            mEditTextLength.isEnabled = false
        } else {
            mEditTextLength.setText("")
            mEditTextLength.isEnabled = true
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mButtonSave -> {
                val id = UUID.randomUUID().toString()
                if (TextUtils.isEmpty(mEditTextFieldNumber.text) ||
                    TextUtils.isEmpty(mEditTextLength.text)
                    || TextUtils.isEmpty(mEditTextFieldName.text)
                ) {
                    Dialog.showMessageDialog(this.getString(R.string.input_empty), this)
                } else {
                    if (field != null) {
                        field?.number = mEditTextFieldNumber.text.toString()
                        field?.name = mEditTextFieldName.text.toString()
                        field?.length = mEditTextLength.text.toString()
                        field?.typeId = value?.typeId.toString()
                        fieldRepository?.onAddOrUpdateField(field)
                        Dialog.showMessageDialog(this.getString(R.string.update_successfully), this)
                    } else {
                        val data = Field(
                            id,
                            mEditTextFieldNumber.text.toString(),
                            mEditTextFieldName.text.toString(),
                            mEditTextLength.text.toString(),
                            value?.typeId!!
                        )
                        fieldRepository?.onAddOrUpdateField(data)
                        Dialog.showMessageDialog(this.getString(R.string.save_successfully), this)

                    }
                    clearData()
                }

            }

            R.id.mButtonClose -> {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            R.id.mButton -> {
                if (!isShowPopup) {
                    showPopup()
                }
            }
        }
    }

    /**
     * Create popup window for cities
     */
    private fun initPopupWindow() {

        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_popup_window, null)

        mAdapterPopup = BaseAdapter()
        view?.mRecyclerView?.layoutManager = LinearLayoutManager(this)
        view?.mRecyclerView?.adapter = mAdapterPopup

        val strList: MutableList<String> =
            mutableListOf("Equipment", "Furniture", "Wall paint", "Mattress", "Wall paper")

        for ((index, value) in strList.withIndex()) {
//            val selected = Selected(value, false)
//            if (index == 0) {
//                selected.selected = true
//                mSelected = selected
//                mButton.text = value
//            }  mButton.text = value
//
            mAdapterPopup?.addItem(PopupItem(this, value, this))
        }

        view?.mRecyclerView?.addItemDecoration(
            DividerItemDecorationMargin(
                resources.getDrawable(R.drawable.divider_line_one_dp),
                Dimension.convertDpToPixel(this, 20f).toInt()
            )
        )

        val metrics = this.resources.displayMetrics

        mPopupWindow = PopupWindow(
            view,
            metrics.widthPixels - Dimension.convertDpToPixel(this, (40).toFloat()).toInt(),
            Dimension.convertDpToPixel(this, (45 * 4).toFloat()).toInt(),
            true
        )


        mPopupWindow?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        mPopupWindow?.isOutsideTouchable = true

        mPopupWindow?.elevation = 20f
    }

    private fun showPopup() {

        if (mAdapterPopup == null) {
            return
        }

        if (mAdapterPopup?.itemCount!! < 4) {
            mPopupWindow?.height = Dimension.convertDpToPixel(this, (45 * mAdapterPopup?.itemCount!!).toFloat()).toInt()
        } else {
            mPopupWindow?.height = Dimension.convertDpToPixel(this, (45 * 4).toFloat()).toInt()
        }

        mPopupWindow?.showAsDropDown(mButton, 0, 0, Gravity.END)
        mPopupWindow?.setOnDismissListener {
            isShowPopup = false
        }
        isShowPopup = true
    }

    override fun onTapItem(text: String?) {
        mButton.text = text
        mPopupWindow?.dismiss()
    }

    private fun clearData() {
        mEditTextFieldNumber.setText("")
        mEditTextFieldName.setText("")
        mEditTextLength.setText("")
        mSpinner.setSelection(0)
    }
}
