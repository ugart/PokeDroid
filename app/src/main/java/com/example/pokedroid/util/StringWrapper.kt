package com.example.pokedroid.util

import android.content.Context
import android.support.annotation.StringRes


class StringWrapper {

    private var mMessageRes: Int? = null
    private var mMessageStr: String? = null

    constructor(@StringRes message: Int) {
        mMessageRes = message
    }

    constructor(message: String) {
        mMessageStr = message
    }

    fun getMessage(context: Context?): String {

        return context?.run {
            mMessageRes?.let { getString(it) } ?: kotlin.run { mMessageStr ?: "" }
        } ?: kotlin.run {
            ""
        }
    }

}
