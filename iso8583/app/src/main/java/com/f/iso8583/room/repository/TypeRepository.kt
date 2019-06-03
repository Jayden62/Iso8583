package com.f.iso8583.room.repository

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import com.f.iso8583.R
import com.f.iso8583.room.dao.TypeDao
import com.f.iso8583.room.database.Database
import com.f.iso8583.room.model.Type
import com.f.iso8583.utils.Constant
import java.sql.Types

class TypeRepository() {


    companion object {
        val TAG = this::class.java.simpleName!!
    }

    private var database: Database? = null
    private var dao: TypeDao? = null

    constructor(context: Context) : this() {
        database = Room.databaseBuilder(context, Database::class.java, Constant.DatabaseName.db_name).build()
        dao = database?.typeDao()
    }

    @SuppressLint("StaticFieldLeak")
    fun addTypes(types: MutableList<Type>?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        object : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg voids: Void): String? {
                types?.let { dao?.addTypes(it) }
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
    fun getAllTypes(): LiveData<MutableList<Type>>? {
        val liveData = MutableLiveData<MutableList<Type>>()
        object : AsyncTask<Void, Void, MutableList<Type>>() {

            override fun doInBackground(vararg voids: Void): MutableList<Type>? {
                val result: MutableList<Type> = mutableListOf()
                dao?.getAllTypes()?.let {
                    result.addAll(it)
                }
                return result
            }

            override fun onPostExecute(result: MutableList<Type>) {
                super.onPostExecute(result)
                liveData.value = result
            }
        }.execute()
        return liveData
    }

    @SuppressLint("StaticFieldLeak")
    fun getTypeName(typeId: String): LiveData<Type?> {
        val liveData = MutableLiveData<Type?>()
        object : AsyncTask<Void, Void, Type?>() {

            override fun doInBackground(vararg voids: Void): Type? {
                return dao?.getTypeName(typeId)
            }

            override fun onPostExecute(result: Type?) {
                super.onPostExecute(result)
                liveData.value = result
            }
        }.execute()
        return liveData
    }

    @SuppressLint("StaticFieldLeak")
    fun removeAllTypes(): MutableLiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        object : AsyncTask<Void, Void, Void?>() {

            override fun doInBackground(vararg voids: Void): Void? {
                 dao?.let { dao?.removeAllTypes() }
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                result.value = true
            }
        }.execute()
        return result
    }
}