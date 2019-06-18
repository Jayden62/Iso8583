package com.f.iso8583.utils

object Constant {

    private var lastVal: Int? = 0

    object DefaultFields {
        const val FIELD_3 = 3
        const val FIELD_4 = 4
        const val FIELD_11 = 11
        const val FIELD_12 = 12
        const val FIELD_13 = 13
        const val FIELD_14 = 14
        const val FIELD_22 = 22
        const val FIELD_23 = 23
        const val FIELD_24 = 24
        const val FIELD_25 = 25
        const val FIELD_35 = 35
        const val FIELD_37 = 37
        const val FIELD_38 = 38
        const val FIELD_39 = 39
        const val FIELD_41 = 41
        const val FIELD_42 = 42
        const val FIELD_55 = 55
        const val FIELD_62 = 62
    }

    object DatabaseName{
        const val db_name = "GINI Database"
    }

    object Data{
        const val REQUEST_CODE_ADD_DATA  = 101
        const val REQUEST_CODE_SCAN  = 210
        const val DATA = "data"
    }

    object METHODS {
        fun getTPDU(message: String?): String? {
            lastVal = 10
            return message?.substring(0, lastVal!!)
        }

        fun getMTI(message: String?): String? {
            lastVal = 14
            return message?.substring(10, lastVal!!)
        }

        fun getBitmap(message: String?): String? {
            lastVal = 30
            return message?.substring(14, lastVal!!)
        }

        fun analyseBitmap(message: String?): String? {
            return message?.substring(lastVal!!, message.length)
        }

        fun getField35(mess: String): String {
            val value = mess.substring(34, 74)
            var newValue: String? = null
            if (value == "37") {
                newValue = mess.substring(36, 74)
            }
            return newValue!!
        }
    }

}