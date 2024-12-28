package com.codewithfk.chatooz_android

import android.app.Application
import com.codewithfk.chatooz_android.utils.Keys.APP_ID
import com.codewithfk.chatooz_android.utils.Keys.APP_SIGN
import com.zegocloud.zimkit.services.ZIMKit

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        ZIMKit.initWith(this, APP_ID, APP_SIGN);
        // Online notification for the initialization (use the following code if this is needed).
        ZIMKit.initNotifications();
    }
}