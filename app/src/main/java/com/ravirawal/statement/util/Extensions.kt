package com.ravirawal.statement.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.text.format.DateUtils
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ravirawal.statement.R
import java.text.SimpleDateFormat
import java.util.*


fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url ?: "")
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .placeholder(R.drawable.ic_newspaper_roll)
        .error(R.drawable.ic_newspaper_roll)
        .into(this)
}

fun ImageView.load(@DrawableRes drawableRes: Int) {
    Glide.with(this).load(drawableRes).fitCenter().into(this)
}

fun String?.default(default: String? = null): String {
    if (this.isNullOrEmpty()) {
        return default ?: ""
    }
    return this
}

inline fun <T : Any> T?.act(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

/**
 * Checks if the device has internet access.
 *
 * @return true if the device is online
 */
@Suppress("DEPRECATION")
fun Context.isOnline(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

/**
 * Checks if the given package name is available on the device.
 *
 * @param targetPackage package name to be checked
 * @return true if available
 */
fun Context.isPackageAvailable(targetPackage: String): Boolean {
    return try {
        packageManager.getPackageInfo(targetPackage, PackageManager.GET_META_DATA)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun AppCompatActivity.setStatusBarTransparent() {
    window.decorView.systemUiVisibility =
        window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
    window.statusBarColor = Color.TRANSPARENT
}

private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
    val win = activity.window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun String?.toRelativeDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    if (this == null) {
        return ""
    }
    try {
        val date: Date = inputFormat.parse(this) ?: return ""
        return DateUtils.getRelativeTimeSpanString(
            date.time,
            Calendar.getInstance().timeInMillis,
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    } catch (e: Exception) {
        return ""
    }


}