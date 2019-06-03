package com.f.iso8583.room.database;

import android.arch.persistence.room.RoomDatabase;
import com.f.iso8583.room.dao.FieldDao;
import com.f.iso8583.room.dao.TypeDao;
import com.f.iso8583.room.model.Field;
import com.f.iso8583.room.model.Type;

@android.arch.persistence.room.Database(entities = {Type.class, Field.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract TypeDao typeDao();

    public abstract FieldDao fieldDao();

}