package com.idax.openvision.Fragment.Option1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.idax.openvision.Entity.User
import com.idax.openvision.Entity.User_
import com.idax.openvision.databinding.FragmentOptionOneBinding
import kotlinx.android.synthetic.main.fragment_option_one.*
import kotlinx.android.synthetic.main.view_one.view.*
import java.util.*
import kotlin.math.round

class OptionOne : Fragment() {

    companion object {
        private const val BOTTOM_SHEET_1 = 12
        private const val ALL = 10
        private const val BOTTOM_SHEET_2 = 13
        private const val DIALOG = 11
    }

    private lateinit var binding: FragmentOptionOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOptionOneBinding.inflate(layoutInflater)
        return binding.root.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val user: User = arguments?.getBundle("package") as User
        val user: User = arguments?.getSerializable("package") as User
        //val user: User = arguments?.getParcelable<User>("package") as User
        //val user: User = arguments?.get("package") as User

        inits()
        listeners()
        setData(user, ALL)
    }

    private fun listeners() {
        binding.dialogButton.setOnClickListener {

        }

        binding.bottomSheetButton.setOnClickListener {

        }
    }

    inline fun Boolean.then(block: () -> Unit) {
        if (this) {
            block()
        }
    }

    private fun setData(user: User, type: Int) {
        val name = user.name
        val lastName = user.lastName
        val height = round(Random().nextDouble() * 100).toFloat()
        val user_ = User_(name, lastName, height)

        if (type == ALL) {
            setDataPrimary(user_)
            setDataSecondary(user_)
            setDataTertiary(user_)
        } else if (type == DIALOG) {
            setDataPrimary(user_)
        } else if (type == BOTTOM_SHEET_1) {
            setDataSecondary(user_)
        } else if (type == BOTTOM_SHEET_2) {
            setDataTertiary(user_)
        }
    }

    private fun setDataPrimary(user_: User_) {
        binding.nameEditText.setText(user_.name)
        binding.lastNameEditText.setText(user_.lastName)
        binding.slider.value = user_.height
    }

    private fun setDataSecondary(user_: User_) {
        layoutInclude.nameEditText_.setText(user_.name)
        layoutInclude.lastNameEditText_.setText(user_.lastName)
        layoutInclude.slider_.value = user_.height
    }

    private fun setDataTertiary(user_: User_) {
        bottomSheetInclude.nameEditText_.setText(user_.name)
        bottomSheetInclude.lastNameEditText_.setText(user_.lastName)
        bottomSheetInclude.slider_.value = user_.height
    }

    private fun inits() {
        bottomSheetInclude.titleTextView_.text = "Custom BottomSheet 2"
        BottomSheetBehavior.from(bottomSheetFrameLayout).apply {
            peekHeight = 150
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}