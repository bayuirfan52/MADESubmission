package com.bayuirfan.madesubmission.model.data

data class Discover<T> (
        val page: Int,
        val total_result: Int,
        val total_pages: Int,
        val result: ArrayList<T>,
        val status_message: String,
        val status_code: Int
)