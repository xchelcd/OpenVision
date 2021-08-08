package com.idax.openvision.Fragment.Main

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.idax.openvision.Adapter.OptionsAdapter
import com.idax.openvision.Entity.Option
import com.idax.openvision.Entity.Options
import com.idax.openvision.Entity.User
import com.idax.openvision.Extra.Permission
import com.idax.openvision.Extra.Permission.Companion.REQUEST_CODE_PERMISSIONS
import com.idax.openvision.R
import com.idax.openvision.databinding.FragmentMainOptionsBinding
import kotlinx.android.synthetic.main.fragment_main_options.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainOptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainOptionsFragment : Fragment() {

    private lateinit var binding: FragmentMainOptionsBinding

    private lateinit var navController: NavController

    private var iteratorButton: Int = 0

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainOptionsBinding.inflate(layoutInflater)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return layoutInflater.inflate(R.layout.fragment_main_options, container, false)
        //return inflater.inflate(R.layout.fragment_main_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inits(view)
        listeners()
        setAdapter()
    }

    fun listeners() {
        buttonButton.setOnClickListener {
            iteratorButton++
            buttonButton.text = "Tap $iteratorButton"
        }
    }


    fun setAdapter() {
        val optionsList: List<Option> = Options().getData()

        val adapter = OptionsAdapter(requireContext(), optionsList) { position ->
            //Toast.makeText(context, "${optionsList[position]}", Toast.LENGTH_SHORT)
            //    .show()
            val fragmentId = if (optionsList.get(position).fragmentId != -1)
                optionsList[position].fragmentId
            else null

            fragmentId?.let {
                if (it == R.id.cameraFragment || it == R.id.opencvFragment)
                    if (Permission.cameraPermissionsGranted(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) && Permission.cameraPermissionsGranted(
                            requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )
                        navController.navigate(it)
                    else
                        activity?.let { it1 ->
                            Permission.requestPermission(
                                this,
                                arrayOf(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                            )
                        }
                else {
                    val bundle = bundleOf(
                        "package" to User(
                            0,
                            "Xchel Alonso",
                            "Carranza De La O",
                            24,
                            "xchel.co@gmail.com"
                        )
                    )
                    navController.navigate(it, bundle)
                }
            }

        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    fun inits(v: View) {
        setData()

        navController = Navigation.findNavController(v)
    }

    private fun setData() {
        // Example of a call to a native method
        titleTextView.text = stringFromJNI()
    }

    external fun stringFromJNI(): String


    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainOptionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainOptionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (Permission.cameraPermissionsGranted(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) && Permission.cameraPermissionsGranted(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                startCamera()
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startCamera() {
        navController.navigate(R.id.cameraFragment)
    }
}