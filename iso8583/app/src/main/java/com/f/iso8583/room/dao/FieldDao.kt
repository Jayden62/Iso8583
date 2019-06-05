package com.f.iso8583.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.f.iso8583.room.model.Field
import com.f.iso8583.room.model.Type

@Dao
interface FieldDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun onAddOrUpdateField(field: Field?)

    @Query("SELECT * FROM fields")
    fun getAllFields(): MutableList<Field>

    @Query("SELECT length FROM fields WHERE number=:number")
    fun getLength(number: String?): String?

    @Query("SELECT fields.*, types.type_name from Fields join types on fields.type_id=types.type_id where number=:search or type_name=:search ")
    fun getFields(search: String?): MutableList<Field>?


    @Query("DELETE FROM fields where id=:id")
    fun deleteField(id: String?)
}