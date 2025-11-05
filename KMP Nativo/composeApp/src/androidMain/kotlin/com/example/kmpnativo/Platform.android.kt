package com.example.kmpnativo

import android.os.Build
import com.example.kmpnativo.model.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()