package com.elmirov.course.util

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.elmirov.course.R
import com.google.android.material.snackbar.Snackbar

private const val EMPTY_STRING = ""

fun Fragment.getErrorSnackBar(
    textResId: Int,
    actionText: String? = null,
    actionListener: View.OnClickListener? = null,
): Snackbar {
    val view = requireActivity().findViewById<View>(android.R.id.content)
    val snack = Snackbar.make(view, getString(textResId), Snackbar.LENGTH_LONG)

    snack.setBackgroundTint(requireContext().getColor(R.color.background_100))
    snack.setTextColor(requireContext().getColor(R.color.text_300))
    snack.setActionTextColor(requireContext().getColor(R.color.tab_indicator))

    snack.setAction(actionText, actionListener)

    return snack
}

fun Fragment.showDialog(
    title: String = EMPTY_STRING,
    layout: View? = null,
    message: String? = null,
    positiveButtonText: String = EMPTY_STRING,
    onPositiveButtonClick: () -> Unit,
    negativeButtonText: String = EMPTY_STRING,
    onNegativeButtonClick: (() -> Unit)? = null,
) {
    AlertDialog.Builder(requireContext(), R.style.AlertDialog).apply {
        setTitle(title)

        if (layout != null)
            setView(layout)

        setMessage(message)

        setPositiveButton(positiveButtonText) { _, _ ->
            onPositiveButtonClick()
        }

        setNegativeButton(negativeButtonText) { _, _ ->
            onNegativeButtonClick?.let { it() }
        }

        create()
        show()
    }
}