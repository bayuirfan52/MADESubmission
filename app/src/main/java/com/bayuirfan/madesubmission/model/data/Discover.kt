package com.bayuirfan.madesubmission.model.data

data class Discover<T> (
        val page: Int?,
        val total_results: Int?,
        val total_pages: Int?,
        val results: List<T>,
        val status_message: String?,
        val status_code: Int?
)