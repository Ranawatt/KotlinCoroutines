package com.example.mindorkscoroutine.utils

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes

/* Set the View Visibility to VISIBLE and eventually animate the view alpha till 100% */
fun View.visible() {
    visibility = View.VISIBLE
}
/* Set the View Visibility to INVISIBLE and eventually animate the view alpha till 0% */
fun View.invisible() {
    hide(View.INVISIBLE)
}
/* Set the View Visibility to GONE and eventually animate the view alpha till 0% */
fun View.gone() {
    hide(View.GONE)
}

private fun View.hide(hidingStrategy: Int) {
    visibility = hidingStrategy
}

/* Convenient method that chooses between View.visible() or View.invisible() methods */
fun View.visibleOrInvisible(show: Boolean) {
    if(show) visible() else invisible()
}
/* Convenient method that chooses between View.visible() or View.gone() methods */
fun View.visibleOrGone(show: Boolean) {
    if (show) visible() else gone()
}

fun Activity.showToast(@StringRes message: Int) {
    Toast.makeText(this.applicationContext,
         this.applicationContext.getText(message), Toast.LENGTH_SHORT).show()
}