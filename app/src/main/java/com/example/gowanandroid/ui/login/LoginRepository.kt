package com.example.gowanandroid.ui.login

import com.example.gowanandroid.model.api.RetrofitClient


/**
 * Created by xiaojianjun on 2019-11-24.
 */
class LoginRepository {
    suspend fun login(username: String, password: String) =
        RetrofitClient.apiService.login(username, password).apiData()
}