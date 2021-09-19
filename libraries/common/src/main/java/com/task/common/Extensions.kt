package com.task.common

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

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


fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}