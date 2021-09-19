package com.task.common

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import android.content.Intent
import android.content.res.ColorStateList
import android.icu.text.CaseMap
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.task.common.Constants.EXPLORATIONURL


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


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun String.comicValueFormat(preFix: String): String {
    val value = if (this.isEmpty()) "N/A" else this
    return "$preFix\n$value"
}


fun AppCompatActivity.openWebBrowser(comicNumber: Int) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("$EXPLORATIONURL$comicNumber"))
    startActivity(browserIntent)
}

fun AppCompatActivity.shareData(title: String, imageUrl: String,description:String,comicNumber:Int) {
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, imageUrl)
        putExtra(Intent.EXTRA_TEXT, "$title\n$description\n$EXPLORATIONURL$comicNumber")
        putExtra(Intent.EXTRA_TITLE, title)

        type = "image/jpeg"
    }
    startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.send_to)))

}


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

fun FloatingActionButton.changeState(isFavorite:Boolean){
    if (isFavorite){
        setImageResource(R.drawable.ic_heart)
        imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.favorite
            )
        )
    }else{
        setImageResource(R.drawable.ic_favorites)
        imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                android.R.color.white
            )
        )
    }

}