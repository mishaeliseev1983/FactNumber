package com.melyseev.factnumber.presentation

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

interface ManageResources {
    fun string(@StringRes id: Int): String

    class Base @Inject constructor(val context: Context): ManageResources{
        override fun string(id: Int): String  = context.getString(id)
    }
}
