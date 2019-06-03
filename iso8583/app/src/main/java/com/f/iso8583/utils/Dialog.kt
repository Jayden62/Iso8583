package com.f.iso8583.utils

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AlertDialog

object Dialog {

    /**
     * Show message dialog
     * @param message
     * @param context
     */
    fun showMessageDialog(message: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton(
            "Oke"
        ) { dialog, which -> dialog.dismiss() }
        builder.setCancelable(false)
        builder.show()
    }

    /**
     * Show loading dialog
     * @param context
     * @param message
     */
    fun showLoadingDialog(context: Context?, message: String): ProgressDialog? {
        val mProgressDialog  = ProgressDialog(context)
        mProgressDialog.setMessage(message)
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
        return mProgressDialog
    }
}