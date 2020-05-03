package com.example.gowanandroid.ui.mine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.gowanandroid.R
import com.example.gowanandroid.ui.base.BaseVmFragment
import com.example.gowanandroid.ui.history.HistoryActivity
import com.example.gowanandroid.util.ActivityManager
import com.example.gowanandroid.util.bus.Bus
import com.example.gowanandroid.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : BaseVmFragment<MineViewModel>() {


    companion object {
        fun newInstance() = MineFragment()
    }

    override fun layoutRes() = R.layout.fragment_mine

    override fun viewModelClass() = MineViewModel::class.java

    override fun initView() {

        llHistory.setOnClickListener {
            ActivityManager.start(HistoryActivity::class.java)
        }
    }



    @SuppressLint("SetTextI18n")
    override fun observe() {
        super.observe()
        mViewModel.run {
            isLogin.observe(viewLifecycleOwner, Observer {
                tvLoginRegister.isGone = it
                tvNickName.isVisible = it
                tvId.isVisible = it
            })
            userInfo.observe(viewLifecycleOwner, Observer {
                it?.let {
                    tvNickName.text = it.nickname
                    tvId.text = "ID: ${it.id}"
                }
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner) {
            mViewModel.getUserInfo()
        }
    }

    override fun initData() {
        mViewModel.getUserInfo()
    }


}
