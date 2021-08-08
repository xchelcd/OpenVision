package com.idax.openvision.Fragment.Option2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.idax.openvision.Entity.User
import com.idax.openvision.R

class OptionTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_option_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val user: User = arguments?.getBundle("package") as User
        val user: User = arguments?.getSerializable("package") as User
        //val user: User = arguments?.getParcelable<User>("package") as User
        //val user: User = arguments?.get("package") as User
        Toast.makeText(context, user.email, Toast.LENGTH_SHORT).show()
    }
}