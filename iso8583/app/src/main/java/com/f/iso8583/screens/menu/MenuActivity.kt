package com.f.iso8583.screens.menu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.f.iso8583.R
import com.f.iso8583.screens.field.FieldActivity
import com.f.iso8583.screens.parser.ParserActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        mButtonParseField.setOnClickListener(this)
        mButtonConfig.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mButtonParseMsg -> {
                startActivity(Intent(this, ParserActivity::class.java))

            }

            R.id.mButtonConfig -> {
                startActivity(Intent(this, FieldActivity::class.java))

            }
        }
    }
}
