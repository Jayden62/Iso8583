package com.f.iso8583.room.model

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import android.os.Parcel
import android.os.Parcelable

@Entity(
    tableName = "fields",
    foreignKeys = [
        ForeignKey(
            entity = Type::class,
            parentColumns = ["type_id"],
            childColumns = ["type_id"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
class Field(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "number")
    var number: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "length")
    var length: String,
    @ColumnInfo(name = "type_id")
    var typeId: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(number)
        parcel.writeString(name)
        parcel.writeString(length)
        parcel.writeString(typeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Field> {
        override fun createFromParcel(parcel: Parcel): Field {
            return Field(parcel)
        }

        override fun newArray(size: Int): Array<Field?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        super.equals(other)

        val that = other as Field
        return this.id == that.id

    }
}