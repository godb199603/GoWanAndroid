package com.example.gowanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.gowanandroid.ui.base.BaseViewModel
import com.example.gowanandroid.util.bus.Bus
import com.example.gowanandroid.util.bus.USER_LOGIN_STATE_CHANGED


/**
 * Created by xiaojianjun on 2019-10-18.
 */
class LoginViewModel : BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }
    val submitting = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()


    fun login(account: String, password: String) {
        submitting.value = true
        launch(
            block = {
                val userInfo = loginRepository.login(account, password)
                userRepository.updateUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED, true)
                submitting.value = false
                loginResult.value = true
            },
            error = {
                submitting.value = false
                loginResult.value = false
            }
        )
    }

}