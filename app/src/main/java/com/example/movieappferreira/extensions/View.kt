package com.example.movieappferreira.extensions

import android.view.View

fun View?.isVisible() = this?.visibility == View.VISIBLE
fun View?.isNotVisible() = this?.visibility == View.GONE || this?.visibility == View.INVISIBLE

fun View.visibility(b: Boolean?, invisible: Boolean = false) {
    this.visibility = if (b == true) View.VISIBLE else if (invisible) View.INVISIBLE else View.GONE
}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}