package com.example.gowanandroid

import android.app.Application
import com.example.gowanandroid.common.ActivityLifecycleCallbacksAdapter
import com.example.gowanandroid.util.ActivityManager




class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
        // 主进程初始化
//        if (isMainProcess(this)) {
//            init()
//        }
    }

    private fun init() {
        rigesterActivityCallbacks()
//        setDayNightMode()
    }

    private fun setDayNightMode() {
//        setNightMode(SettingsStore.getNightMode())
    }

    private fun rigesterActivityCallbacks() {
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksAdapter(
            onActivityCreated = { activity, _ ->
                ActivityManager.activities.add(activity)
            },
            onActivityDestroyed = { activity ->
                ActivityManager.activities.remove(activity)
            }
        ))
    }
}