package com.f.iso8583.screens.parser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.f.iso8583.utils.Constant
import com.f.iso8583.R
import com.f.iso8583.list.BaseAdapter
import kotlinx.android.synthetic.main.activity_parser.*
import java.math.BigInteger
import java.util.regex.Pattern


class ParserActivity : AppCompatActivity() {

    private var mAdapter: BaseAdapter<Any>? = null

    private var result: String? = null

    private var length: Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parser)
        mAdapter = BaseAdapter()
        mRecyclerView?.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        mRecyclerView?.adapter = mAdapter


        val message =
            "600002000002003020058020C000040000000000015000000000030022000200375124140000024012D12011010000080100000035393030303935303030303030303030303038373439340006303030303031"
        val value = removeWhiteSpaces(message)

        mEditTextMessage.setText(value)
        mTextViewMessageLength.text = "Không có length"

        mTextViewTPDU.text = Constant.METHODS.getTPDU(value)
        mTextViewMTI.text = Constant.METHODS.getMTI(value)
        mTextViewBitmap.text = Constant.METHODS.getBitmap(value)
        val bitmap = Constant.METHODS.getBitmap(value)
        val binary = hexToBinary(bitmap)
        mTextViewBinary?.text = binary

        val data = Constant.METHODS.analyseBitmap(value)
        for (field in 1..binary.length) {
            if (binary[field - 1].toString() == "1") {
                print("field : $field")
                when (field) {
                    Constant.DefaultFields.FIELD_3 -> {
                        result = data.substring(0, 6)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_4 -> {
                        result = data.substring(6, 18)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_11 -> {
                        result = data.substring(18, 24)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_22 -> {
                        result = data.substring(24, 28)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_24 -> {
                        result = data.substring(28, 32)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_25 -> {
                        result = data.substring(32, 34)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_35 -> {
                        result = data.substring(36, 74)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_41 -> {
                        result = data.substring(74, 90)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_42 -> {
                        result = data.substring(90, 120)
                        length = result?.length
                    }
                    Constant.DefaultFields.FIELD_62 -> {
                        result = data.substring(124, data.length)
                        length = result?.length
                    }
                }
                mAdapter?.addItem(
                    ParserItem(
                        this,
                        "FIELD : $field --- [ $length ] ",
                        result
                    )
                )
            }
        }
    }

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