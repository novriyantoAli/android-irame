package co.id.digital.insinyur.irame.util

import android.view.View

fun View.visibleWhen(show: Boolean){
    if (show) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}