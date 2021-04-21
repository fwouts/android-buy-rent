package com.fwouts.buyrent.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fwouts.buyrent.R

class PropertyListAdapter :
    PagingDataAdapter<PropertyViewModel, PropertyListAdapter.ViewHolder>(PropertyViewModel.COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_property_card, parent, false)
        val loadingAnimation = CircularProgressDrawable(parent.context)
        loadingAnimation.strokeWidth = 5f
        loadingAnimation.centerRadius = 30f
        return ViewHolder(view, loadingAnimation)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { viewModel ->
            with(viewModel) {
                holder.price.text = price
                holder.description.text = description
                holder.address.text = address
                holder.loadingAnimation.start()
                val glide = Glide.with(holder.itemView)
                glide.load(viewModel.imageUrl)
                    .centerCrop()
                    .placeholder(holder.loadingAnimation).into(holder.image)
                glide.load(viewModel.agencyLogoUrl).into(holder.agencyLogo)
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.loadingAnimation.stop()
    }

    class ViewHolder(view: View, val loadingAnimation: CircularProgressDrawable) :
        RecyclerView.ViewHolder(view) {
        val price: TextView = view.findViewById(R.id.price)
        val description: TextView = view.findViewById(R.id.description)
        val address: TextView = view.findViewById(R.id.address)
        val image: ImageView = view.findViewById(R.id.image)
        val agencyLogo: ImageView = view.findViewById(R.id.agency_logo)
    }
}