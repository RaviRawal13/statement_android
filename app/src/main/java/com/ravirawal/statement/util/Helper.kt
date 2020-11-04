package com.ravirawal.statement.util

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun share(title: String, url: String, context: Context?) {
    if (context == null) {
        return
    }
    try {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, title)
        val body =
            "Checkout this article\n${title}\n$url"
        i.putExtra(Intent.EXTRA_TEXT, body)
        context.startActivity(Intent.createChooser(i, "Share:"))
    } catch (e: Exception) {
        Toast.makeText(context, "Unable to share this article", Toast.LENGTH_SHORT).show()
    }
}