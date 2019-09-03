package com.bayuirfan.myfavorite.utils

import android.database.Cursor

interface LoadDataCallback {
    fun postExecute(cursor: Cursor)
}