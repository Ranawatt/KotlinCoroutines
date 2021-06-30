package com.example.mindorkscoroutine.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.mindorkscoroutine.R
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

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

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackBar(snackbarText: String, timeLength: Int){
    Snackbar.make(this, snackbarText, timeLength).run { show() }
}
/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
//fun View.setupSnackBar(
//    lifecycleOwner: LifecycleOwner, snackBarEvent: LiveData<Event<Int>>, timeLength: Int) {
//
//}
fun View?.layoutInflater() {
    LayoutInflater.from(this?.context)
}

fun Fragment?.layoutInflater() {
    LayoutInflater.from(this?.requireContext())
}

fun Fragment.setupRefreshLayout(
    refreshLayout: ScrollChildSwipeRefreshLayout,
    scrollUpChild: View?= null) {

    refreshLayout.setColorSchemeColors(
        ContextCompat.getColor(requireActivity(), R.color.colorAccent),
        ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
        ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)
    )

    scrollUpChild?.let {
        refreshLayout.scrollUpChild = it
    }
}

fun Activity.showToast(@StringRes message: Int) {
    Toast.makeText(this.applicationContext,
         this.applicationContext.getText(message), Toast.LENGTH_SHORT).show()
}