package com.idax.openvision.Fragment.OptionCamera

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface.ROTATION_180
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.idax.openvision.R
import com.idax.openvision.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater)
        return layoutInflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        imageViewCam.scaleType = ImageView.ScaleType.CENTER_CROP
        imageViewCV.scaleType = ImageView.ScaleType.FIT_CENTER

        startCamera()
    }

    private fun listeners() {
        takePhotoButton.setOnClickListener { takePhoto() }
    }

    private fun takePhoto() {
        val bitmap = viewFinder.bitmap
        imageViewCam.setImageBitmap(bitmap)
        imageViewCV.setImageBitmap(viewFinder.bitmap)


        //val file =
        //    File("${Environment.getExternalStorageDirectory()}/${System.currentTimeMillis()}.png")

        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.getDefault()
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        ///storage/emulated/0/Android/media/com.idax.openvision
        //file:///storage/emulated/0/Android/media/com.idax.openvision/Open%20Vision/2021-07-17-16-37-53-284.jpg
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                    Toast.makeText(requireContext(), "${exc.message}", Toast.LENGTH_LONG).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    //NOT SAVED


                    //file:///storage/emulated/0/Android/media/com.idax.openvision/Open%20Vision/2021-07-17-16-37-53-284.jpg
                    //val savedUri = Uri.fromFile(photoFile)
                    //val msg = "Photo capture succeeded: $savedUri"
                    //Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    //Log.d(TAG, msg)
                }
            })
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
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                    //ImageReader.
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setTargetRotation(ROTATION_180)
                .build()
                .also {
                    /*it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })*/
                    it.setAnalyzer(cameraExecutor, { image ->

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
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    /*private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }*/

    fun getOutputDirectory(): File {
        //->/storage/9016-4EF8/Pictures/IDAX
        //->/storage/emulated/0/Android/media/com.idax.openvision
        val mediaDir = requireContext().externalMediaDirs?.firstOrNull()?.let {
            File(it, requireContext().resources.getString(R.string.app_name)).apply { mkdirs() } }

        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}