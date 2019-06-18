package com.f.iso8583.screens.scan

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.f.iso8583.R
import kotlinx.android.synthetic.main.activity_scan.*
import android.Manifest.permission
import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeCallback
import android.media.RingtoneManager
import com.f.iso8583.utils.Constant

class ScanActivity : AppCompatActivity() {


    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        requestPermission()

        mDecoratedBarcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                Log.d(TAG, result.toString())
                beepSound()
                val intent = Intent()
                intent.putExtra(Constant.Data.DATA, result.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {

            }
        })

    }

    override fun onResume() {
        super.onResume()
        resumeScanner()
    }

    private fun resumeScanner() {
//        isScanDone = false
        if (!mDecoratedBarcodeView.isActivated)
            mDecoratedBarcodeView.resume()
        Log.d(TAG, "paused: false")
    }

    private fun pauseScanner() {
        mDecoratedBarcodeView.pause()
    }

    override fun onPause() {
        super.onPause()
        pauseScanner()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }
    }

    private fun beepSound() {
        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isEmpty()) {
            requestPermission()
        } else {
            mDecoratedBarcodeView.resume()
        }
    }
}
