package com.f.iso8583.list

import android.content.Context
import android.view.View

/**
 * Created by VANNAM on 2017-11-20.
 */
abstract class BaseItem<T>(var mContext: Context) {
    /**
     * Type as header, item, footer.
     */
    protected var mType = 0

    var mData: T? = null

    constructor(mContext: Context, mData: T?) : this(mContext) {
        this.mData = mData
    }

    /**
     * Init layout
     */
    abstract fun onInitLayout(): Int

    /**
     * Bind view
     */
    abstract fun onBindView(view: View?)


}