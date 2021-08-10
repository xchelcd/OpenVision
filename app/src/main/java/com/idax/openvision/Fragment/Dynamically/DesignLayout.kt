package com.idax.openvision.Fragment.Dynamically

import android.content.Context
import com.idax.openvision.Extra.CustomView.CustomCell

class DesignLayout(val context: Context, val size: Int) {

    companion object {
        private val coeficients = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "= ♦")
    }

    private var biArray: Array<FloatArray>? = null
    private val cellList: MutableList<CustomCell> = ArrayList()

}

/**
private void metodoGaussJordan() {
String cadena = "";
resultadosCadena = "";
int filas = numEc;
int columnas = filas + 1;
float pivote, cero;
for (int i = 0; i < filas; i++) {
pivote = cellsArray[i][i];
if (pivote == 0) {
}
for (int j = 0; j < columnas; j++) {
cellsArray[i][j] /= pivote;
}
for (int k = 0; k < filas; k++) {
if (k != i) {
cero = cellsArray[k][i];
for (int j = 0; j < columnas; j++) {
cellsArray[k][j] = (cellsArray[k][j] - cero * cellsArray[i][j]);
}
}
}
}
resultadosFloat = new ArrayList<>();
for (int i = 0; i < filas; i++) {
resultadosFloat.add(cellsArray[i][filas]);
resultadosCadena += (letras[i] + "= " + cellsArray[i][filas] + "\n");
}
}
 */