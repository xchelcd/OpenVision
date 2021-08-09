package com.idax.openvision.Extra

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import com.idax.openvision.R

class CustomLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayoutCompat(context, attrs) {

    var ratio = 1F

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CustomLinearLayout)
        ratio = attr.getFloat(R.styleable.CustomLinearLayout_ratio, ratio)
        attr.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val (w, h) = Utils.getMeasure(measuredWidth, measuredHeight, ratio)

        setMeasuredDimension(w, h)
    }
}