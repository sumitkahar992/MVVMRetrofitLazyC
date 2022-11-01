package com.example.mvvmretrofitlazyc

import java.io.Serializable


data class MoviesModel(
    val category: String,
    val desc: String,
    val imageUrl: String,
    val name: String
) : Serializable


