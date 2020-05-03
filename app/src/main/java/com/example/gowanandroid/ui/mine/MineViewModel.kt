package com.example.gowanandroid.ui.mine

import androidx.lifecycle.MutableLiveData
import com.example.gowanandroid.model.bean.UserInfo
import com.example.gowanandroid.ui.base.BaseViewModel



class MineViewModel : BaseViewModel() {

    private val mineRespository by lazy { MineRespository() }

    val userInfo = MutableLiveData<UserInfo?>()
    val isLogin = MutableLiveData<Boolean>()

    fun getUserInfo() {
        isLogin.value = userRepository.isLogin()
        userInfo.value = userRepository.getUserInfo()
    }
}
