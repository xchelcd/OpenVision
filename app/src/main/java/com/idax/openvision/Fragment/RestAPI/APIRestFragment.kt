package com.idax.openvision.Fragment.RestAPI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.idax.openvision.Entity.UserVAX
import com.idax.openvision.Fragment.RestAPI.Request.APIPresenter
import com.idax.openvision.Fragment.RestAPI.Request.APIView
import com.idax.openvision.R
import com.idax.openvision.databinding.FragmentApiRestBinding
import kotlinx.android.synthetic.main.fragment_api_rest.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIRestFragment : Fragment(), APIView {

    private lateinit var binding: FragmentApiRestBinding

    private lateinit var presenter: APIPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentApiRestBinding.inflate(layoutInflater)
        return layoutInflater.inflate(R.layout.fragment_api_rest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inits()
        listeners()
    }

    private fun listeners() {
        apiButton.setOnClickListener {
            presenter.getUserById(id = 0)
        }
    }

    private fun inits() {
        presenter = APIPresenter(this)
    }


    override fun onSuccess(user: UserVAX) {
        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.setMargin(20F, 0F)
        toast.setText("Hola mundo:\n$user")
        toast.show()
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }
}