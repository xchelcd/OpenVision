package com.idax.openvision.Fragment.Maps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.slider.Slider
import com.idax.openvision.R
import com.idax.openvision.databinding.FragmentMapsBinding
import kotlinx.android.synthetic.main.fragment_maps.*


class MapsFragment : Fragment(), OnMapReadyCallback {

    companion object {
        private const val REQUEST_CODE_AUTOCOMPLETE_FROM = 1
        private const val REQUEST_CODE_AUTOCOMPLETE_TO = 2

        private val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
    }

    private val TAG = "OpenVisionMapsFragment"

    private lateinit var map: GoogleMap

    private lateinit var binding: FragmentMapsBinding

    private var lat = 21.0051109
    private var lng = -89.5669681
    private var latlngFrom = LatLng(lat, lng)
    private var latlngTo = LatLng(lat, lng)
    private var title = "Default"
    private var markerFrom: Marker? = null
    private var markerTo: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //REQUIRE BILLING
        //Places.initialize(requireContext(), resources.getString(R.string.API_KEY_MAPS))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapsBinding.inflate(layoutInflater)

        return binding.root.rootView
        //return layoutInflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        inits()
        listeners()
    }

    /**
     * IMPROVE FUNCTIONS
     */
    private fun listeners() {
        binding.latSlider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            if (binding.fromRadioButton.isChecked) {
                markerFrom?.remove()
                lat = value.toDouble()
                latlngFrom = LatLng(lat, lng)
                markerFrom = addMarker(latlngFrom, title)
            } else {
                markerTo?.remove()
                lat = value.toDouble()
                latlngTo = LatLng(lat, lng)
                markerTo = addMarker(latlngTo, title)
            }


            binding.dataTextView.setText("Lat: $lat, Lng: $lng")
        })
        binding.lngSlider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            if (binding.fromRadioButton.isChecked) {
                markerFrom?.remove()
                lng = value.toDouble()
                latlngFrom = LatLng(lat, lng)
                markerFrom = addMarker(latlngFrom, title)
            } else {
                markerTo?.remove()
                lng = value.toDouble()
                latlngTo = LatLng(lat, lng)
                markerTo = addMarker(latlngTo, title)
            }

            binding.dataTextView.setText("Lat: $lat, Lng: $lng")
        })
        binding.titleTextView.doOnTextChanged { text, start, before, count ->
            title = text.toString()
        }
        binding.fromRadioButton.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                lat = latlngFrom.latitude
                lng = latlngFrom.longitude
                binding.latSlider.value = lat.toFloat()
                binding.lngSlider.value = lng.toFloat()
            } else {
                lat = latlngTo.latitude
                lng = latlngTo.longitude
                binding.latSlider.value = latlngTo.latitude.toFloat()
                binding.lngSlider.value = latlngTo.longitude.toFloat()
            }
        }
    }

    private fun addMarker(latLng: LatLng, title: String): Marker? {
        val markerOptions = MarkerOptions().apply {
            position(latLng)
            title(title)
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }
        val marker = map.addMarker(markerOptions)
        return marker
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        markerFrom = addMarker(latlngFrom, title)
    }

    private fun inits() {
        binding.latSlider.value = lat.toFloat()
        binding.lngSlider.value = lng.toFloat()
    }

    /**
     *REQUIRE ENABLE BILLING ON GOOGLE CLOUD
     * fromButton.setOnClickListener {
     *      autocomplete(REQUEST_CODE_AUTOCOMPLETE_FROM)
     * }
     * toButton.setOnClickListener {
     *      autocomplete(REQUEST_CODE_AUTOCOMPLETE_TO)
     * }
     */
    val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
        }
    }

    private fun autocomplete(requestCode: Int) {
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(requireContext())
        startActivityForResult(intent, requestCode)
        startForResult.launch(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_FROM) {
            proccessAutocompleteResult(resultCode, data) {
                //set place in view
                it.latLng?.let {
                    latlngFrom = it
                }
                //markerOption(latlng, "From")
            }
        } else if (requestCode == REQUEST_CODE_AUTOCOMPLETE_TO) {
            proccessAutocompleteResult(resultCode, data) {
                //set place in view
                it.latLng?.let {
                    latlngFrom = it
                }
                //markerOption(latlng, "To")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun proccessAutocompleteResult(
        resultCode: Int,
        data: Intent?,
        callback: (Place) -> Unit,
    ) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    Log.d(TAG, "Place: ${place.name}, ${place.id}")
                    callback(place)
                }
            }
            AutocompleteActivity.RESULT_ERROR -> {
                // TODO: Handle the error.
                data?.let {
                    val status = Autocomplete.getStatusFromIntent(data)
                    status.statusMessage?.let {
                        Log.d(TAG, it)
                    }
                }
            }
        }
        return
    }

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}