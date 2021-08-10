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
import androidx.core.view.marginTop
import androidx.core.widget.addTextChangedListener
import com.idax.openvision.Extra.CustomView.CustomCell
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

    private fun createTableLayout(): TableLayout = TableLayout(context).apply {
        layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT).apply {
            val margin = 15
            setMargins(margin, margin, margin, margin)
        }
    }

    private fun createTableRow(): TableRow = TableRow(context).apply {
        layoutParams = createParamsForRow()
        gravity = Gravity.CENTER
    }

    private fun createParamsForRow(): TableLayout.LayoutParams = TableLayout.LayoutParams(
        TableLayout.LayoutParams.MATCH_PARENT,
        TableLayout.LayoutParams.WRAP_CONTENT).apply {
        val margin = 10
        setMargins(margin, margin, margin, margin)
    }

    /**
     * onTextHasChanged -> CustomCell
     */
    private fun createCell(tableRow: TableRow): CustomCell =
        CustomCell(requireContext(), viewGroup = tableRow) { text, editText ->
            //CustomCellChangeListener
            //Log.d(TAG, "$text->${v.text}")
            //Log.d(TAG, "createCell -> ${cellList[0].editText.text}")
        }

    private val cellList: MutableList<CustomCell> = ArrayList()
    private fun createTable(size: Int) {
        val tableLayout = createTableLayout()
        for (i in 1..size) {
            val tableRow = createTableRow()
            for (j in 1..size + 1) {
                val cell = createCell(tableRow)
                cellList.add(cell)
            }
            tableLayout.addView(tableRow)
        }
        matrixView.addView(tableLayout)
        ansView.addView(createSolveButton(size))
    }

    private fun resetMatrix() {
        matrixView.removeAllViews()
        matrixHeaderView?.removeAllViews()
    }

    private val coeficients = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "= ♦")
    private var biArray: Array<FloatArray>? = null

    /**
     * Call when solveButton is pressed
     * Before check that all cells are filled
     */
    private fun listToBiArray(size: Int) {
        Log.d(TAG, "listToBiArray")
        //if (size != cellList.size) return
        biArray = Array(size) { FloatArray(size + 1) }
        var index = 0
        for (i in 0 until size)
            for (j in 0..size) {
                biArray!![i][j] = "${cellList[index].editText.text}".toFloat()
                Log.d(TAG, "listToBiArray -> $biArray")
                index += index
            }
    }

    private fun createMatrixHeader(size: Int): LinearLayout = LinearLayout(context).apply {
        for (i in 0..size) {
            this.addView(TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    weight = 1f
                }
                isEnabled = false
                text = if (i == size) "= ♦" else coeficients[i]
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                textSize = 18f
                setTextColor(Color.BLACK)
            })
        }
    }

    private fun createHeader(): LinearLayout = createLinearLayout().apply {
        addView(createLabel("Num of square matrix:"))
        addView(createEditText("#"))
    }

    private var matrixHeaderView: LinearLayout? = null
    private fun createButton(title: String = "Create table"): Button = Button(context).apply {
        layoutParams = params3()
        text = title
        setOnClickListener {
            size?.let { size ->
                resetMatrix()
                matrixHeaderView = createMatrixHeader(size)
                mainView.addView(matrixHeaderView)
                createTable(size)
            }
        }
    }

    private fun createSolveButton(size: Int): Button = Button(context).apply {
        text = "Solve by Gauss Jordan"
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
            val padding = 15
            setPadding(padding, padding, padding, padding)
            topMargin = padding
        }
        setBackgroundColor(resources.getColor(R.color.purple_500, context.theme))
        setOnClickListener {
            listToBiArray(size)
            GaussJordan(size)
        }
    }

    private fun createEditText(hintLabel: String): EditText = EditText(context).apply {
        layoutParams = params2()
        hint = hintLabel
        setTextColor(Color.BLACK)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        inputType = InputType.TYPE_CLASS_NUMBER
        addTextChangedListener {
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

    private fun GaussJordan(size: Int) {
        Log.d(TAG, "GaussJordan")
        var resultadosCadena = ""
        val filas = size
        val columnas = size + 1
        var pivote: Float?
        var cero: Float?
        for (i in 0 until filas) {//until size
            pivote = biArray!![i][i]
            if (pivote == 0f) {
            }
            for (j in 0 until columnas) {//..size
                biArray!![i][j] /= pivote;
            }
            for (k in 0 until filas) {//until size
                if (k != i) {
                    cero = biArray!![k][i];
                    for (j in 0 until columnas) {//..size
                        biArray!![k][j] = (biArray!![k][j] - cero * biArray!![i][j]);
                    }
                }
            }
        }
        val resultadosFloat = arrayListOf<Float>()
        for (i in 0 until filas) {//until size
            resultadosFloat.add(biArray!![i][filas]);
            resultadosCadena += (coeficients[i] + "= " + biArray!![i][filas] + "\n");
        }
        Log.d(TAG, "GaussJordan -> $resultadosCadena")
    }
}
