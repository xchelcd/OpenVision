package com.idax.openvision.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.idax.openvision.Entity.Option
import com.idax.openvision.R
import com.idax.openvision.ViewHolder.OptionsViewHolder

class OptionsAdapter(
    val context: Context,
    val optionsList: List<Option>,
    private val itemClickListener: (Int) -> Unit,
) :
    RecyclerView.Adapter<OptionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_option, parent, false)
        return OptionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.optionTitleButton.text = optionsList.get(position).title
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide))
        holder.bind(itemClickListener)
    }

    override fun getItemCount() = optionsList.size

}
