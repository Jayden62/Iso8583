package com.f.iso8583.room.repository

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import com.f.iso8583.room.dao.FieldDao
import com.f.iso8583.room.database.Database
import com.f.iso8583.room.model.Field
import com.f.iso8583.utils.Constant

class FieldRepository() {

    companion object {
        val TAG = FieldRepository::class.java.simpleName!!
    }

    private var database: Database? = null
    private var dao: FieldDao? = null
    private var mProgressDialog: ProgressDialog? = null

    constructor(context: Context) : this() {
        database = Room.databaseBuilder(context, Database::class.java, Constant.DatabaseName.db_name).build()
        dao = database?.fieldDao()
        mProgressDialog = ProgressDialog(context)
    }

    @SuppressLint("StaticFieldLeak")
    fun onAddOrUpdateField(field: Field?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        object : AsyncTask<Void, Void, String>() {

            override fun onPreExecute() {
                super.onPreExecute()
                mProgressDialog?.setCancelable(false)
                mProgressDialog?.show()
            }

            override fun doInBackground(vararg voids: Void): String? {
                field?.let { dao?.onAddOrUpdateField(it) }
                return ""
            }

            override fun onPostExecute(aVoid: String) {
                super.onPostExecute(aVoid)
                result.value = true
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
            }
        }.execute()
        return result
    }

    @SuppressLint("StaticFieldLeak")
    fun getAllFields(): LiveData<MutableList<Field>>? {
        val liveData = MutableLiveData<MutableList<Field>>()
        object : AsyncTask<Void, Void, MutableList<Field>>() {

            override fun onPreExecute() {
                super.onPreExecute()
                mProgressDialog?.setMessage("Loading data...")
                mProgressDialog?.setCancelable(false)
                mProgressDialog?.show()
            }

            override fun doInBackground(vararg voids: Void): MutableList<Field>? {
                val result: MutableList<Field> = mutableListOf()
                dao?.getAllFields()?.let {
                    result.addAll(it)
                }
                return result
            }

            override fun onPostExecute(result: MutableList<Field>) {
                super.onPostExecute(result)
                liveData.value = result
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
            }
        }.execute()
        return liveData
    }

    @SuppressLint("StaticFieldLeak")
    fun getFields(typeName: String?): LiveData<MutableList<Field>>? {
        val liveData = MutableLiveData<MutableList<Field>>()
        object : AsyncTask<Void, Void, MutableList<Field>>() {

            override fun onPreExecute() {
                super.onPreExecute()
                mProgressDialog?.setMessage("Loading data...")
                mProgressDialog?.setCancelable(false)
                mProgressDialog?.show()
            }

            override fun doInBackground(vararg params: Void?): MutableList<Field>? {
                val result: MutableList<Field> = mutableListOf()
                dao?.getFields(typeName)?.let {
                    result.addAll(it)
                }
                return result
            }

            override fun onPostExecute(result: MutableList<Field>?) {
                super.onPostExecute(result)
                liveData.value = result
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
            }
        }.execute()
        return liveData
    }


    @SuppressLint("StaticFieldLeak")
    fun findField(number: String): LiveData<Field?> {
        val liveData = MutableLiveData<Field?>()
        object : AsyncTask<Void, Void, Field?>() {
            override fun onPreExecute() {
                super.onPreExecute()
                mProgressDialog?.setCancelable(false)
                mProgressDialog?.show()
            }


            override fun doInBackground(vararg voids: Void): Field? {
                return dao?.findField(number)
            }

            override fun onPostExecute(result: Field?) {
                super.onPostExecute(result)
                liveData.value = result
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
            }
        }.execute()
        return liveData
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteField(id: String?) {
        object : AsyncTask<Void, Void, Boolean>() {
            override fun onPreExecute() {
                super.onPreExecute()
                mProgressDialog?.setCancelable(false)
                mProgressDialog?.show()
            }

            override fun doInBackground(vararg voids: Void): Boolean? {
                dao?.deleteField(id)
                return true
            }

            override fun onPostExecute(result: Boolean?) {
                super.onPostExecute(result)
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
            }
        }.execute()
    }
}