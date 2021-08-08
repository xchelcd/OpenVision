package com.idax.openvision.Fragment.RoomDB

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idax.openvision.Entity.User
import com.idax.openvision.R
import com.idax.openvision.databinding.FragmentRoomInfoBinding
import kotlinx.android.synthetic.main.fragment_room_info.*

class RoomInfoFragment : Fragment() {

    private val TAG = "OpenVisionRoomInfoFragment"

    private lateinit var binding: FragmentRoomInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animamtion =
            TransitionInflater.from(requireContext()).inflateTransition(
                android.R.transition.move)
        sharedElementEnterTransition = animamtion
        sharedElementReturnTransition = animamtion
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRoomInfoBinding.inflate(layoutInflater)

        return layoutInflater.inflate(R.layout.fragment_room_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user: User = arguments?.getSerializable("user") as User
        bindView(user)
        Log.d(TAG, "$user")
    }

    private fun bindView(user: User?) {
        user?.let {
            coverImageView.setImageDrawable(resources.getDrawable(R.mipmap.ic_launcher, resources.newTheme()))
            with(it) {
                idTextView.text = "id: $id"
                ageTextView.text = "age: $age"
                fullNameTextView.text = "$name $lastName"
                emailTextView.text = email
            }
        }
    }


}