package com.fwouts.buyrent.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fwouts.buyrent.R

class PropertyCardViewHolder(view: View, private val loadingAnimation: CircularProgressDrawable) :
    RecyclerView.ViewHolder(view) {
    companion object {
        fun create(parent: ViewGroup): PropertyCardViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_property_card, parent, false)
            val loadingAnimation = CircularProgressDrawable(parent.context)
            loadingAnimation.strokeWidth = 5f
            loadingAnimation.centerRadius = 30f
            return PropertyCardViewHolder(view, loadingAnimation)
        }
    }

    private val price: TextView = view.findViewById(R.id.price)
    private val description: TextView = view.findViewById(R.id.description)
    private val address: TextView = view.findViewById(R.id.address)
    private val image: ImageView = view.findViewById(R.id.image)
    private val agencyLogo: ImageView = view.findViewById(R.id.agency_logo)

    fun bind(viewModel: PropertyCardViewModel) {
        price.text = viewModel.price
        description.text = viewModel.description
        address.text = viewModel.address
        loadingAnimation.start()
        val glide = Glide.with(itemView)
        glide.load(viewModel.imageUrl)
            .centerCrop()
            .placeholder(loadingAnimation).into(image)
        glide.load(viewModel.agencyLogoUrl).into(agencyLogo)
    }

    fun onRecycled() {
        loadingAnimation.stop()
    }
}