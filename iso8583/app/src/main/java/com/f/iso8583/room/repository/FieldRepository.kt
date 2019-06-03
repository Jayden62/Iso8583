package com.f.iso8583.room.repository

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
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

    constructor(context: Context) : this() {
        database = Room.databaseBuilder(context, Database::class.java, Constant.DatabaseName.db_name).build()
        dao = database?.fieldDao()
    }

    @SuppressLint("StaticFieldLeak")
    fun onAddOrUpdateField(field: Field?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        object : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg voids: Void): String? {
                field?.let { dao?.onAddOrUpdateField(it) }
                return ""
            }

            override fun onPostExecute(aVoid: String) {
                super.onPostExecute(aVoid)
                result.value = true
            }
        }.execute()
        return result
    }

    @SuppressLint("StaticFieldLeak")
    fun getAllFields(): LiveData<MutableList<Field>>? {
        val liveData = MutableLiveData<MutableList<Field>>()
        object : AsyncTask<Void, Void, MutableList<Field>>() {

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

            }
        }.execute()
        return liveData
    }

    @SuppressLint("StaticFieldLeak")
    fun getField(number: String): LiveData<Field?> {
        val liveData = MutableLiveData<Field?>()
        object : AsyncTask<Void, Void, Field?>() {

            override fun doInBackground(vararg voids: Void): Field? {
                return dao?.getField(number)
            }

            override fun onPostExecute(result: Field?) {
                super.onPostExecute(result)
                liveData.value = result
            }
        }.execute()
        return liveData
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteField(id: String?) {
        object : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg voids: Void): Boolean? {
                dao?.deleteField(id)
                return true
            }
        }.execute()
    }
}