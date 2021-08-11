package com.idax.openvision.Extra.CustomView

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.idax.openvision.R


/**
 * DISUSE
 */
@SuppressLint("ViewConstructor")
class CustomCell @JvmOverloads constructor(
    context: Context,
    tag: Int,
    //viewGroup: ViewGroup,
    textHasChanged: (String, EditText) -> Unit,
) : AppCompatEditText(context, null, 0) {

    private val TAG = "OpenVisionDynamicFragment"

    var editText: EditText

    init {
        val v: View = LayoutInflater.from(context).inflate(R.layout.custom_cell, null, false)
        editText = v.findViewById(R.id.customCellEditText)
        editText.tag = tag
        Log.d(TAG, "CustomCell ID -> ${editText.id}")
        Log.d(TAG, "CustomCell TAG -> ${editText.tag}")
        editText.doOnTextChanged { text, start, before, count ->
            //editText.setText(s)//Checar porque se atora el teclado y aún no sé qué es
            textHasChanged("$text", editText)
        }
    }
}