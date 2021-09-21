package com.task.common

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.task.common.Constants.EXPLORATIONURL

// load image from url or res id
fun ImageView.loadImage(url: Any?) {
    url?.let {
        if (url is Int) {
            Picasso.get()
                .load(url)
                .into(this)

            setImageResource(url)
        }

        if (url is String)
            Picasso.get()
                .load(url)
                .into(this)
    }
}

// make view visible extension
fun View.visible() {
    visibility = View.VISIBLE
}

// replace empty value with n/a extension
fun String.comicValueFormat(preFix: String): String {
    val value = if (this.isEmpty()) "N/A" else this
    return "$preFix\n$value"
}

// open wev browser extension
fun AppCompatActivity.openWebBrowser(comicNumber: Int) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("$EXPLORATIONURL$comicNumber"))
    startActivity(browserIntent)
}

// share data action extension
fun AppCompatActivity.shareData(
    title: String,
    imageUrl: String,
    description: String,
    comicNumber: Int
) {
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, imageUrl)
        putExtra(
            Intent.EXTRA_TEXT,
            "$title\n$description\n$EXPLORATIONURL$comicNumber"
        )
        putExtra(Intent.EXTRA_TITLE, title)

        type = "image/jpeg"
    }
    startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.send_to)))
}

// open activity extension
fun Context.openActivity(className: Class<*>, bundle: Bundle? = null, closeAll: Boolean = false) {

    val intent = Intent(this, className)
    bundle?.let {
        intent.putExtras(it)
    }

    if (closeAll) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    startActivity(intent)
}

// toggle floating button status  extension
fun FloatingActionButton.changeState(isFavorite: Boolean) {
    if (isFavorite) {
        setImageResource(R.drawable.ic_heart)
        imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.favorite
            )
        )
    } else {
        setImageResource(R.drawable.ic_favorites)
        imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                android.R.color.white
            )
        )
    }
}
