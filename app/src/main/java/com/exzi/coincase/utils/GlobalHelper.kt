package com.exzi.coincase.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.view.WindowManager
import com.exzi.coincase.MainActivity
import com.exzi.coincase.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream

object GlobalHelper {

    private fun setupInputError(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
        error: String?,
        isChange: Boolean
    ) {
        if (error != null) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = error
            if (isChange) textInputEditText.setBackgroundResource(R.drawable.background_input_error)
        } else {
            textInputLayout.isErrorEnabled = false
            textInputLayout.error = null
            if (isChange) textInputEditText.setBackgroundResource(R.drawable.background_input)
        }
    }

    fun setTouchable(activity: MainActivity, setTouchable: Boolean) {
        if (setTouchable) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    fun encodeImageBase64(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun checkEmptyEdittext(
        context: Context,
        editText: TextInputEditText,
        textInputLayout: TextInputLayout,
        isChange: Boolean = true
    ) {
        val value = editText.text.toString()
        if (value.isEmpty()) {
            setupInputError(
                textInputLayout,
                editText,
                context.getString(R.string.cannot_be_empty),
                isChange
            )
        } else {
            setupInputError(textInputLayout, editText, null, isChange)
        }
    }
}