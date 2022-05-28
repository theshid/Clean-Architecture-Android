package com.example.ecomkt.app.util

import android.os.Build
import androidx.annotation.IntRange

private const val minVersion = Build.VERSION_CODES.M.toLong()
private const val maxVersion = Build.VERSION_CODES.Q.toLong()

fun versionFrom(@IntRange(from = minVersion, to = maxVersion) versionCode: Int): Boolean =
    Build.VERSION.SDK_INT >= versionCode