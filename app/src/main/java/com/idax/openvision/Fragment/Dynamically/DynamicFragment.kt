package com.idax.openvision.Fragment.Dynamically

import android.graphics.Color
import android.icu.lang.UCharacter
import android.os.Bundle
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.idax.openvision.R
import com.idax.openvision.databinding.FragmentDynamicBinding
import kotlinx.android.synthetic.main.fragment_dynamic.*


class DynamicFragment : Fragment() {

    private val TAG = "OpenVisionDynamicFragment"

    lateinit var binding: FragmentDynamicBinding

    private var size: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDynamicBinding.inflate(layoutInflater)
        return layoutInflater.inflate(R.layout.fragment_dynamic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView.addView(createHeader())
        mainView.addView(createButton())
    }

    private fun createHeader(): LinearLayout {
        val linearLayout = createLinearLayout()
        linearLayout.addView(createLabel("Num of square matrix:"))
        linearLayout.addView(createEditText("#"))
        return linearLayout
    }

    private fun createTable(size: Int) {
        val tableLayout = TableLayout(context)
        tableLayout.layoutParams = params4()

        for (i in 1..size) {
            val row = newRow()
            for (j in 1..size) {
                row.addView(createCell(i, j))
            }
            tableLayout.addView(row)
        }
        addTable(tableLayout)
    }

    private val cellList = arrayOf<Array<EditText>>()
    private fun createCell(i: Int, j: Int): EditText {
        val cell = EditText(context)

        cellList[i][j] = cell
        return cell
    }

    private fun newRow(): TableRow {
        val row = TableRow(context)
        return row
    }

    private fun addTable(tableLayout: TableLayout) {
        mainView.addView(tableLayout)
    }

    private fun createButton(title: String = "Create table"): Button {
        val button = Button(context)
        button.layoutParams = params3()
        button.text = title
        button.setOnClickListener {
            size?.let { size ->
                Log.d(TAG, size.toString())
                createTable(size)
            }
        }
        return button
    }

    private fun createEditText(hint: String): EditText {
        val editText = EditText(context)
        editText.layoutParams = params2()
        editText.hint = hint
        editText.setTextColor(Color.BLACK)
        editText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.addTextChangedListener {
            //Log.d(TAG, it.toString())
            size = it.toString().toIntOrNull()
        }
        return editText
    }

    private fun createLabel(text: String): TextView {
        val label = TextView(context)
        label.text = text
        label.setTextColor(Color.BLACK)
        label.textAlignment = View.TEXT_ALIGNMENT_CENTER
        return label
    }

    private fun createLinearLayout(): LinearLayout {
        val layout = LinearLayout(context)
        layout.layoutParams = params1()
        layout.orientation = LinearLayout.HORIZONTAL
        layout.gravity = Gravity.CENTER
        return layout
    }

    private fun params1(): LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        return params
    }

    private fun params2(): LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        return params
    }

    private fun params3(): LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        return params
    }

    private fun params4(): LinearLayout.LayoutParams =
        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
}