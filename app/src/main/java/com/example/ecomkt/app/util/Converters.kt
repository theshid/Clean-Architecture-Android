package com.example.ecomkt.app.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG,100,stream)
    return stream.toByteArray()
}

fun Bitmap.toBase64():String{
    return this.toByteArray().toBase64()
}

fun ByteArray.toBase64():String{
    return Base64.encodeToString(this,Base64.DEFAULT)
}

fun ByteArray.toBitmap():Bitmap{
    return BitmapFactory.decodeByteArray(this,0,this.size)
}

fun String.toByteArrayBis(): ByteArray? {
    if (this.isNotEmpty()){
        return Base64.decode(this.toByteArray(),Base64.DEFAULT)
    }
    return null
}

fun String.toBitmap(): Bitmap? {
    return this.toByteArrayBis()?.toBitmap()
}


