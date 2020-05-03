package com.example.gowanandroid.ui.common

import com.example.gowanandroid.model.api.RetrofitClient
import com.example.gowanandroid.model.bean.UserInfo
import com.example.gowanandroid.model.store.UserInfoStore


open class UserRepository {

    fun updateUserInfo(userInfo: UserInfo) = UserInfoStore.setUserInfo(userInfo)

    fun isLogin() = UserInfoStore.isLogin()

    fun getUserInfo() = UserInfoStore.getUserInfo()

    fun clearLoginState() {
        UserInfoStore.clearUserInfo()
        RetrofitClient.clearCookie()
    }

}