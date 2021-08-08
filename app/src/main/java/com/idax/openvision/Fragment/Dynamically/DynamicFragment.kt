package com.idax.openvision.Fragment.Dynamically

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
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

    private fun createHeader(): LinearLayout = createLinearLayout().apply {
        addView(createLabel("Num of square matrix:"))
        addView(createEditText("#"))
    }

    private fun createTable(size: Int) {
        val table = TableLayout(context)

        setupTable(table)

        for (i in 1..size) {
            val row = newRow()
            for (j in 1..size) {
                //row.addView(createCell(i, j))
            }
            //table.addView(row)
        }
        addTable(table)
    }

    private val cellList = arrayOf<Array<EditText>>()
    private fun createCell(i: Int, j: Int): EditText {
        val cell = EditText(context)//weight = 1

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

    private fun createButton(title: String = "Create table"): Button = Button(context).apply {
        layoutParams = params3()
        text = title
        setOnClickListener {
            size?.let { size ->
                Log.d(TAG, size.toString())
                createTable(size)
            }
        }
    }

    private fun createEditText(hintLabel: String): EditText = EditText(context).apply {
        layoutParams = params2()
        hint = hintLabel
        setTextColor(Color.BLACK)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        inputType = InputType.TYPE_CLASS_NUMBER
        addTextChangedListener {
            //Log.d(TAG, it.toString())
            size = it.toString().toIntOrNull()
        }
    }

    private fun createLabel(title: String): TextView = TextView(context).apply {
        text = title
        setTextColor(Color.BLACK)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
    }

    private fun createLinearLayout(): LinearLayout = LinearLayout(context).apply {
        layoutParams = params1()
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
    }

    private fun setupTable(table: TableLayout) {
        val padding = 35
        val margin = 35
        table.layoutParams = params4(margin)
        table.setBackgroundColor(resources.getColor(R.color.purple_500, resources.newTheme()))
        table.setPadding(padding, padding, padding, padding)
    }

    private fun params1(): LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT).apply {
        gravity = Gravity.CENTER
    }


    private fun params2(): LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        100, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
        gravity = Gravity.CENTER
    }

    private fun params3(): LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT).apply {
        gravity = Gravity.CENTER
    }

    private fun params4(margin: Int): LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT).apply {
        setMargins(margin, margin, margin, margin)
    }
}