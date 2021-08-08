package com.idax.openvision.Fragment.Maps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idax.openvision.R
import com.idax.openvision.databinding.FragmentMapsBinding


class MapsFragment : Fragment() {

    private val TAG = "OpenVisionMapsFragment"

    private lateinit var binding: FragmentMapsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        return layoutInflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}