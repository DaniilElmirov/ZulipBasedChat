package com.elmirov.course.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.getErrorSnackBar(
    textResId: Int,
    actionText: String? = null,
    actionListener: View.OnClickListener? = null,
): Snackbar {
    val view = requireActivity().findViewById<View>(android.R.id.content)
    val snack = Snackbar.make(view, getString(textResId), Snackbar.LENGTH_LONG)
    snack.setAction(actionText, actionListener)

    return snack
}