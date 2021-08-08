package com.idax.openvision.Extra

import android.annotation.SuppressLint
import android.graphics.*
import android.media.Image
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

typealias LumaListener = (luma: Double) -> Unit

class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {

        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()

        listener(luma)

        image.close()
    }
}
typealias TestListener = (testVar: String, bitmap_1: Bitmap?/*, bitmap_2:Bitmap*/) -> Unit

class TestingAnalyzer(private val listener: TestListener) : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {

        val bitmap_1: Bitmap? = image.image?.run {
            this.toBitmap()
        }
        //var bitmap_2: Bitmap? = null
        //if (image.image != null)
        //    bitmap_2 = image.convertImageProxyToBitmap()

        listener("(${image.height}, ${image.width})", bitmap_1/*, bitmap_2*/)
        image.close()
    }

    private fun Image.toBitmap(): Bitmap {
        val yBuffer = planes[0].buffer // Y
        val vuBuffer = planes[2].buffer // VU

        val ySize = yBuffer.remaining()
        val vuSize = vuBuffer.remaining()

        val nv21 = ByteArray(ySize + vuSize)

        yBuffer.get(nv21, 0, ySize)
        vuBuffer.get(nv21, ySize, vuSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()
        return rotateBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size))
        //return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    /**
     * UNUSEFUL
     */
    fun ImageProxy.convertImageProxyToBitmap(): Bitmap {
        val buffer = planes[0].buffer
        buffer.rewind()
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun rotateBitmap(bitmap: Bitmap, angle: Float = 90F): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}



