package com.example.gopickup.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/* Hide Keyboard */
fun Activity.hideKeyboard() {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus
    if (view != null) {
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    val snackbar = Snackbar.make(this, message, duration)
    snackbar.show()
}

fun View.showGreenSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    val snackbar = Snackbar.make(this, message, duration)
//    snackbar.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
//    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.colorPrimary))
    snackbar.show()
}
