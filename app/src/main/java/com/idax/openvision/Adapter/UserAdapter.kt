package com.idax.openvision.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idax.openvision.Entity.User
import com.idax.openvision.R
import com.idax.openvision.ViewHolder.OptionsViewHolder
import com.idax.openvision.ViewHolder.UserViewHolder

class UserAdapter(
    val context: Context,
    val userList: List<User>,
    private val itemClickListener: (Int) -> Unit,
) : RecyclerView.Adapter<UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(userList[position], context)
        holder.bindListener(itemClickListener)
    }

    override fun getItemCount() = userList.size
}