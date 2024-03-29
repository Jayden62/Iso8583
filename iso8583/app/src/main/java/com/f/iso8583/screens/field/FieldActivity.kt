package com.f.iso8583.screens.field

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.f.iso8583.R
import com.f.iso8583.room.model.Field
import kotlinx.android.synthetic.main.activity_field.*
import android.view.View
import android.widget.*
import com.f.iso8583.list.BaseAdapter
import com.f.iso8583.room.repository.FieldRepository
import com.f.iso8583.screens.newfield.NewFieldActivity
import android.arch.lifecycle.Observer
import com.f.iso8583.room.repository.TypeRepository
import com.f.iso8583.utils.Constant
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import com.f.iso8583.common.EmptyItem
import com.f.iso8583.common.NotFoundItem

class FieldActivity : AppCompatActivity(), FieldItem.Callback, View.OnClickListener, TextWatcher {


    private var TAG = this.javaClass.simpleName

    private var mAdapter: BaseAdapter<Any> = BaseAdapter()

    private var fieldRepository: FieldRepository? = null

    private var typeRepository: TypeRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field)
        fieldRepository = FieldRepository(this)
        typeRepository = TypeRepository(this)

        mFloatingAdd?.setOnClickListener(this)
        mImageViewSearch?.setOnClickListener(this)
        mEditTextSearch.addTextChangedListener(this)
        mAdapter = BaseAdapter()
//        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?

        mRecyclerView?.adapter = mAdapter
        initData()
    }

    private fun initData() {
        mAdapter.removeAllItem()
        fieldRepository?.getAllFields()?.observe(this,
            Observer<MutableList<Field>> { data ->
                if (data!!.size == 0) {
                    mAdapter.addItem(EmptyItem(this))
                } else {
                    for (value in data) {
                        typeRepository?.getTypeName(value.typeId)?.observe(
                            this, Observer {
                                mAdapter.addItem(FieldItem(this, value, it?.typeName, this))
                            }
                        )

                    }
                }
            })
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.toString().isEmpty()) {
            initData()
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Call onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Call onDestroy")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "Call onBackPressed")
    }

    override fun onTapDeleteItem(data: Field?) {
        fieldRepository?.deleteField(data?.id)
        val pos = data?.let { mAdapter.getPosition(it) }
        pos?.let { mAdapter.removeItemAt(it) }
        if (mAdapter.itemCount == 0) {
            mAdapter.addItem(EmptyItem(this))
        }
    }

    override fun onTapEditItem(data: Field?) {
        val intent = Intent(this, NewFieldActivity::class.java)
        intent.putExtra(Constant.Data.DATA, data)
        startActivityForResult(intent, Constant.Data.REQUEST_CODE_ADD_DATA)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mFloatingAdd -> {
                val intent = Intent(this, NewFieldActivity::class.java)
                startActivityForResult(intent, Constant.Data.REQUEST_CODE_ADD_DATA)
            }

            R.id.mImageViewSearch -> {
                if (TextUtils.isEmpty(mEditTextSearch.text)) {
                    Toast.makeText(this, this.getString(R.string.search_empty), Toast.LENGTH_SHORT).show()
                } else {
                    /* Search field */
                    fieldRepository?.getFields(mEditTextSearch.text.toString())?.observe(this, Observer { fields ->
                        mAdapter.removeAllItem()

                        if (fields.isNullOrEmpty() || fields.size == 0) {
                            mAdapter.addItem(NotFoundItem(this))
                        } else {
                            for (it in fields) {
                                typeRepository?.getTypeName(it.typeId)?.observe(this, Observer { type ->
                                    mAdapter.addItem(FieldItem(this, it, type?.typeName, this))
                                })

                            }
                        }

                    })
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.Data.REQUEST_CODE_ADD_DATA && resultCode == Activity.RESULT_OK) {
            initData()
        }
    }
}
