package com.bayuirfan.myfavorite.model

data class MovieModel (
        val id: Int,
        val id_data: Int,
        val title: String,
        val poster_path: String,
        val backdrop_path: String,
        val overview: String,
        val vote_average: String,
        val release_date: String
)