package com.bayuirfan.madesubmission.utils

interface LoadDataCallback<T> {
    fun onPreExecute()
    fun onPostExecute(list: ArrayList<T>)
}