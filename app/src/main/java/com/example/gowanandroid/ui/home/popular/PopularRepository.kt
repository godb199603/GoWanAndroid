package com.example.gowanandroid.ui.home.popular

import com.example.gowanandroid.model.api.RetrofitClient


class PopularRepository {

    suspend fun getTopArticleList() = RetrofitClient.apiService.getTopArticleList().apiData()
    suspend fun getArticleList(page: Int) = RetrofitClient.apiService.getArticleList(page).apiData()
}