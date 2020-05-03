package com.example.gowanandroid.model.store

import com.example.gowanandroid.App
import com.example.gowanandroid.model.bean.UserInfo
import com.example.gowanandroid.util.clearSpValue
import com.example.gowanandroid.util.getSpValue
import com.example.gowanandroid.util.putSpValue
import com.google.gson.Gson


object UserInfoStore {

    private const val SP_USER_INFO = "sp_user_info"
    private const val KEY_USER_INFO = "userInfo"
    private val mGson by lazy { Gson() }

    fun isLogin(): Boolean {
        val userInfoStr = getSpValue(SP_USER_INFO, App.instance, KEY_USER_INFO, "")
        return userInfoStr.isNotEmpty()
    }

    fun getUserInfo(): UserInfo? {
        val userInfoStr = getSpValue(SP_USER_INFO, App.instance, KEY_USER_INFO, "")
        return if (userInfoStr.isNotEmpty()) {
            mGson.fromJson(userInfoStr, UserInfo::class.java)
        } else {
            null
        }
    }

    fun setUserInfo(userInfo: UserInfo) =
        putSpValue(SP_USER_INFO, App.instance, KEY_USER_INFO, mGson.toJson(userInfo))

    fun clearUserInfo() {
        clearSpValue(SP_USER_INFO, App.instance)
    }
}