package com.idax.openvision.ViewHolder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.idax.openvision.Entity.Option
import com.idax.openvision.Entity.Options
import com.idax.openvision.R

class OptionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val optionTitleButton: TextView = view.findViewById(R.id.optionTitleButton)
    val cardView: CardView = view.findViewById(R.id.cardView)

    fun bind(itemClickListener: (Int) -> Unit) {
        itemView.setOnClickListener { itemClickListener(absoluteAdapterPosition) }
    }
}