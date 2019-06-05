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
import android.content.Intent
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

class NewFieldActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    private var types: MutableList<String> = mutableListOf("n", "z", "an", "ans", "b")

    private var typeRepository: TypeRepository? = null

    private var fieldRepository: FieldRepository? = null

    private var value: Type? = null

    private var field: Field? = null

    private var TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_field)
        mButtonSave.setOnClickListener(this)
        mButtonClose.setOnClickListener(this)

        typeRepository = TypeRepository(this)
        fieldRepository = FieldRepository(this)
        mEditTextFieldNumber.addTextChangedListener(this)
        initData()

        if (intent.hasExtra(Constant.Data.DATA)) {
            field = intent.getParcelableExtra(Constant.Data.DATA)

            mEditTextFieldNumber.setText(field?.number)
            mEditTextFieldName.setText(field?.name)
            mEditTextLength.setText(field?.length)
        }

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
        }
    }

    private fun clearData() {
        mEditTextFieldNumber.setText("")
        mEditTextFieldName.setText("")
        mEditTextLength.setText("")
        mSpinner.setSelection(0)
    }
}
