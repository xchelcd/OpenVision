package com.idax.openvision.Extra.CustomView

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.idax.openvision.R

@SuppressLint("ViewConstructor")
class CustomCell @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    viewGroup: ViewGroup,
    textHasChanged: (String, EditText) -> Unit,
) : AppCompatEditText(context, attrs, defStyleAttr) {

    var editText: EditText

    init {
        val v: View = LayoutInflater.from(context).inflate(R.layout.custom_cell, viewGroup, true)
        editText = v.findViewById(R.id.customCellEditText)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    editText.setText(s)
                    textHasChanged("$it", editText)
                }
            }
        })
    }
}