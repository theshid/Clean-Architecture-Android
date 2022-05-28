package com.example.ecomkt.app.util

import android.app.Activity
import android.view.View
import com.example.ecomkt.R
import com.google.android.material.snackbar.Snackbar

internal fun Activity.showSnackbar(view: View, message: String, isError: Boolean = false) {
    val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    if (isError)
        sb.setBackgroundTint(loadColor(R.color.md_red_300))
            .setTextColor(loadColor(R.color.md_red_300))
            .show()
    else
        sb.setBackgroundTint(loadColor(R.color.md_green_300))
            .setTextColor(loadColor(R.color.md_green_300))
            .show()

}