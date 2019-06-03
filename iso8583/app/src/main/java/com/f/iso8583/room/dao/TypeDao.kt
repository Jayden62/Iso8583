package com.f.iso8583.room.dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import com.f.iso8583.room.model.Type

@Dao
interface TypeDao {
    @Insert
    fun addTypes(types: MutableList<Type>?)

    @Query("SELECT * FROM types")
    fun getAllTypes(): MutableList<Type>

    @Query("SELECT * FROM types WHERE type_id=:typeId")
    fun getTypeName(typeId: String): Type?

    @Query("DELETE FROM types")
    fun removeAllTypes()
}