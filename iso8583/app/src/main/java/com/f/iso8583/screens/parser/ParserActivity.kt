package com.f.iso8583.screens.parser

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.f.iso8583.utils.Constant
import com.f.iso8583.R
import com.f.iso8583.list.BaseAdapter
import com.f.iso8583.room.repository.FieldRepository
import com.f.iso8583.room.repository.TypeRepository
import kotlinx.android.synthetic.main.activity_parser.*
import java.math.BigInteger
import java.util.regex.Pattern


class ParserActivity : AppCompatActivity() {

    private var mAdapter: BaseAdapter<Any>? = null

    private var result: String? = null

    private var length: Int? = 0

    private var originLength: String? = null

    private var newLength: String? = null

    private var currentValue: Int = 0

    private var mFieldRepos: FieldRepository? = null

    private var mTypeRepos: TypeRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parser)
        mFieldRepos = FieldRepository(this)
        mTypeRepos = TypeRepository(this)
        mAdapter = BaseAdapter()
        mRecyclerView?.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        mRecyclerView?.adapter = mAdapter


        val message =
            "6000020000 0200 3020078020C00204 000000 000001500000 000004 0052 0000 0002 00 374214946500001019D150522114335042000000 3539303030393530 303030303030303030303837343934\n" +
                    " 0125 5F2A02070482027C008407A0000000031010950500000080009A031504079C01009F02060000000150009F03060000000000009F0902008C9F100706010A03A000029F1A0207049F2608BD30E5F3F12B475D9F2701809F3303E020C89F34031E03009F3501229F360208A09F3704F08BEB8C9F4104000000015F340100 00 06 303030303031\n"
        val value = removeWhiteSpaces(message)

        mEditTextMessage.setText(value)
//        mTextViewMessageLength.text = "Không có length"

        mTextViewTPDU.text = Constant.METHODS.getTPDU(value)
        mTextViewMTI.text = Constant.METHODS.getMTI(value)
        mTextViewBitmap.text = Constant.METHODS.getBitmap(value)
        val bitmap = Constant.METHODS.getBitmap(value)
        val binary = hexToBinary(bitmap)
        mTextViewBinary?.text = binary

        originLength = Constant.METHODS.analyseBitmap(value)
        for (field in 1..binary.length) {
            if (binary[field - 1].toString() == "1") {
                print("field : $field")
                when (field) {
                    Constant.DefaultFields.FIELD_3 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_3.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_4 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_4.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_11 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_11.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_12 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_12.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_13 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_13.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_14 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_14.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_22 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_22.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_23 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_23.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_24 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_24.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_25 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_25.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_35 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_35.toString())?.observe(this, Observer {
                            val newLength = originLength?.length?.let { it ->
                                originLength?.substring(
                                    currentValue,
                                    it
                                )
                            }
                            print(newLength)
                            var receiveValue = 0
                            val value = newLength?.substring(0, 2)
                            if (value == "37") {
                                receiveValue = value.toInt() + 1
                            }
                            val mLength = newLength?.substring(2, newLength.length)
                            print(mLength)
                            result = mLength?.substring(0, receiveValue)
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += receiveValue
                        })
                    }
                    Constant.DefaultFields.FIELD_37 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_37.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_38 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_38.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_39 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_39.toString())?.observe(this, Observer {
                            result = it?.toInt()?.let { it -> originLength?.substring(currentValue, currentValue + it) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_41 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_41.toString())?.observe(this, Observer {
                            result = it?.toInt()
                                ?.let { it -> originLength?.substring(currentValue + 2, currentValue + it + 2) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_42 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_42.toString())?.observe(this, Observer {
                            result = it?.toInt()
                                ?.let { it -> originLength?.substring(currentValue + 2, currentValue + it + 2) }
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += it?.toInt()!!
                        })
                    }
                    Constant.DefaultFields.FIELD_55 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_55.toString())?.observe(this, Observer {
                            val newLength = originLength?.length?.let { it ->
                                originLength?.substring(
                                    currentValue,
                                    it
                                )
                            }
                            print(newLength)
                            val value = newLength?.substring(2, 6)
                            val receiveValue = value!!.toInt() * 2
                            val mLength = newLength.substring(6, newLength.length)
                            print(mLength)
                            result = mLength.substring(0, receiveValue)
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += receiveValue
                        })
                    }
                    Constant.DefaultFields.FIELD_62 -> {
                        mFieldRepos?.getLength(Constant.DefaultFields.FIELD_62.toString())?.observe(this, Observer {
                            val newLength = originLength?.length?.let { it ->
                                originLength?.substring(
                                    currentValue,
                                    it
                                )
                            }
                            print(newLength)
                            val value = newLength?.substring(6, 10)
                            val receiveValue = value!!.toInt() * 2
                            val mLength = newLength.substring(10, newLength.length)
                            print(mLength)
                            result = mLength.substring(0, receiveValue)
                            length = result?.length
                            mAdapter?.addItem(
                                ParserItem(
                                    this,
                                    "FIELD : $field --- [ $length ] ",
                                    result
                                )
                            )
                            currentValue += receiveValue
                        })
                    }
                }
            }
        }
    }

    /**
     * Remove space
     * @param msg
     */
    private fun removeWhiteSpaces(msg: String?): String {
        if (msg!!.isEmpty() || msg.isBlank()) {
            return ""
        }
        var msgPattern = msg
        val pattern = "[\\s]"
        val replace = ""
        val p = Pattern.compile(pattern)
        val m = p.matcher(msgPattern)
        msgPattern = m.replaceAll(replace)
        return msgPattern
    }

    /**
     * Convert hex to binary
     * @param hex
     *
     */
    private fun hexToBinary(hex: String): String {
        val len = hex.length * 4
        var bin = BigInteger(hex, 16).toString(2)
        if (bin.length < len) {
            val diff = len - bin.length
            var pad = ""
            for (i in 0 until diff) {
                pad += "0"
            }
            bin = pad + bin
        }
        return bin
    }
}