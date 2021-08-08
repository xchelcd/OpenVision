package com.idax.openvision.Fragment.OptionOpenCV


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ImageReader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.idax.openvision.Extra.TestingAnalyzer
import com.idax.openvision.R
import com.idax.openvision.databinding.FragmentOpencvBinding
import kotlinx.android.synthetic.main.fragment_opencv.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class OpencvFragment : Fragment() {

    private lateinit var binding: FragmentOpencvBinding

    private lateinit var cameraExecutor: ExecutorService

    //private lateinit var textureView: TextureView
    //private lateinit var opencvImageView: ImageView
    //private lateinit var changeViewToggleButton: ToggleButton
    //private lateinit var linearLayout: LinearLayout

    private lateinit var imageCapture: ImageCapture
    private lateinit var imageAnalysis: ImageAnalysis
    private lateinit var preview: Preview

    var srcBitmap: Bitmap? = null
    var dstBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOpencvBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return layoutInflater.inflate(R.layout.fragment_opencv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()

        //srcBitmap = BitmapFactory.decodeResource(this.resources, R.drawable.parris)
        //dstBitmap = srcBitmap!!.copy(srcBitmap!!.config, true)
        ////
        //opencvImageView.setImageBitmap(srcBitmap)
        //////opencvImageView.setImageResource(R.drawable.parris)
//
        ////doBlur(srcBitmap!!, dstBitmap!!, 5F)
//
//
//
        cameraExecutor = Executors.newSingleThreadExecutor()
        startCamera()
    }

    private fun listeners() {
        //changeViewCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
        //    cameraTextureView.visibility = if (isChecked) View.VISIBLE else View.GONE
        //    opencvImageView.visibility = if (!isChecked) View.VISIBLE else View.GONE
        //}
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview
                .Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFindercv.surfaceProvider)
                    //ImageReader.
                }
            var a = 0
            Log.d("xchel", "${a}")
            ImageReader.OnImageAvailableListener {
                a++
                Log.d("xchel", "${a}")
            }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setTargetRotation(Surface.ROTATION_180)
                .build()
                .also { it ->
                    //it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                    //    Log.d(TAG, "Average luminosity: $luma")
                    //})
                    it.setAnalyzer(cameraExecutor,
                        TestingAnalyzer { screenSize, bitmap_1/*, bitmap_2*/ ->
                            //Log.d(TAG, screenSize)
                            requireActivity().runOnUiThread {
                                dstBitmap = bitmap_1!!.copy(bitmap_1.config, true)
                                doBlur(bitmap_1, dstBitmap!!, 5F)
                                //testingLibs(bitmap_1, dstBitmap!!)

                                try {
                                    opencvImageView.setImageBitmap(dstBitmap)
                                }catch (e:Exception){}
                            }
                        })

                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )

            } catch (exc: Exception) {
                //Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }


    //external fun stringFromJNI(): String
    external fun doBlur(bitmpaIn: Bitmap, bitmapOut: Bitmap, sigma: Float)
    external fun testingLibs(bitmpaIn: Bitmap, bitmapOut: Bitmap)

    companion object {
        private const val TAG = "OpenCVFragment"

        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}