package com.idax.openvision.Fragment.RoomDB

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.idax.openvision.Adapter.UserAdapter
import com.idax.openvision.Entity.User
import com.idax.openvision.R
import com.idax.openvision.Room.AppDatabase
import com.idax.openvision.Room.UserDao
import com.idax.openvision.databinding.FragmentRoomBinding
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.fragment_room.view.*
import kotlinx.android.synthetic.main.item_user.*


class RoomFragment : Fragment() {

    private val TAG = "OpenVisionRoomFragment"

    private lateinit var binding: FragmentRoomBinding

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    private var adapter: UserAdapter? = null
    private var user: User? = null
    private var userList: MutableList<User>? = null
    private var userPosition: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRoomBinding.inflate(layoutInflater)
        return layoutInflater.inflate(R.layout.fragment_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inits()
        setAdapter(userList)

        startAnimating()

        listeners()
    }

    private fun setAdapter(userList: List<User>?) {
        userList?.let {
            adapter = UserAdapter(requireContext(), it) { index ->
                userPosition = index
                user = it.get(index)
                user?.let { user ->
                    fillData(user)
                }
                //appbar.setExpanded(true, true)

                //val extra = FragmentNavigatorExtras(
                //    recyclerView.get(index) to "image_small")
                val bundle = bundleOf("user" to user)
                findNavController().navigate(
                    R.id.action_mainOptions_to_roomInfo,
                    bundle,)
                    //null,
                    //extra)
            }
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
    }

    private fun fillData(user: User) {
        with(user) {
            userId.text = "id: ${id}"
            nameEditText.text = Editable.Factory.getInstance().newEditable(name)
            lastNameEditText.text = Editable.Factory.getInstance().newEditable(lastName)
            ageEditText.text = Editable.Factory.getInstance().newEditable("$age")
            emailEditText.text = Editable.Factory.getInstance().newEditable(email)
        }
    }

    private fun listeners() {
        viewUsersButton.setOnClickListener {
            appbar.setExpanded(false, true)
            //Log.d(TAG, "${appbar.top}")//0
            //Log.d(TAG, "${appbar.bottom}")//1116
        }

        addUserButton.setOnClickListener {
            if (user != null) return@setOnClickListener
            val name = nameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val age = ageEditText.text.toString().toIntOrNull()
            val email = emailEditText.text.toString()

            if (checkEmpyFields(name, lastName, age, email)) {
                user = User(0, name, lastName, age!!, email)
                userDao.insertNewUser(user!!)
                userList?.add(user!!)
                user = null
                newItem()
            }
        }

        updateUserButton.setOnClickListener {
            if (user == null) return@setOnClickListener
            else {
                val name = nameEditText.text.toString()
                val lastName = lastNameEditText.text.toString()
                val age = ageEditText.text.toString().toIntOrNull()
                val email = emailEditText.text.toString()
                if (checkEmpyFields(name, lastName, age, email)) {
                    user?.apply {
                        this.name = name
                        this.lastName = lastName
                        this.age = age!!
                        this.email = email
                    }
                    userDao.updateUser(user!!)
                    updateItem()
                    clearFields()
                }
            }
        }

        deleteUserButton.setOnClickListener {
            if (user == null) return@setOnClickListener
            else userDao.deleteUser(user!!)
            userList?.remove(user)
            deleteItem()
            clearFields()
        }
    }

    private fun updateItem() = userPosition?.let { adapter?.notifyItemChanged(it) }
    private fun deleteItem() = userPosition?.let { adapter?.notifyItemRemoved(it) }
    private fun newItem() = adapter?.notifyItemInserted(userList!!.size)


    private fun clearFields() {
        user = null
        userId.text = Editable.Factory.getInstance().newEditable("id:")
        nameEditText.text = Editable.Factory.getInstance().newEditable("")
        lastNameEditText.text = Editable.Factory.getInstance().newEditable("")
        ageEditText.text = Editable.Factory.getInstance().newEditable("")
        emailEditText.text = Editable.Factory.getInstance().newEditable("")
    }

    private fun checkEmpyFields(name: String, lastName: String, age: Int?, email: String) =
        name.length >= 3 && lastName.length >= 3 && age.toString().length >= 1 && isValidEmail(email)

    private fun isValidEmail(target: CharSequence) =
        !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()

    private fun inits() {
        db = AppDatabase.getAppDataBase(requireContext())
        userDao = db.userDao()

        if (userDao.getAllUsers().isEmpty()) {
            noUserTextView.visibility = View.VISIBLE
            setVisibility(false)
        } else {
            noUserTextView.visibility = View.GONE
            userList = userDao.getAllUsers()
            setVisibility(true)
        }
        Log.d(TAG, "${userDao.getAllUsers()}")
    }

    private fun setVisibility(visibility: Boolean) {
        recyclerView.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun startAnimating() {
        nameEditText.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide))
        lastNameEditText.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide))
        ageEditText.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide))
        emailEditText.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide))
    }
}