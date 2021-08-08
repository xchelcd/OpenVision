package com.idax.openvision.ViewHolder

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.idax.openvision.Entity.User
import com.idax.openvision.R

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    private var cardView: CardView = itemView.findViewById(R.id.cardView)

    fun bindData(user: User, context: Context) {
        nameTextView.text = user.name
        cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.animation_one))
    }

    fun bindListener(itemClickListener: (Int) -> Unit) {
        itemView.setOnClickListener { itemClickListener(absoluteAdapterPosition) }
    }
}