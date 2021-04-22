package com.fwouts.buyrent.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fwouts.buyrent.databinding.ViewPropertyCardBinding

class PropertyCardViewHolder(
    private val binding: ViewPropertyCardBinding,
    private val loadingAnimation: CircularProgressDrawable
) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): PropertyCardViewHolder {
            val binding = ViewPropertyCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            val loadingAnimation = CircularProgressDrawable(parent.context)
            loadingAnimation.strokeWidth = 5f
            loadingAnimation.centerRadius = 30f
            return PropertyCardViewHolder(binding, loadingAnimation)
        }
    }

    fun bind(viewModel: PropertyCardViewModel) {
        binding.price.text = viewModel.price
        binding.description.text = viewModel.description
        binding.address.text = viewModel.address
        loadingAnimation.start()
        val glide = Glide.with(itemView)
        glide.load(viewModel.imageUrl)
            .centerCrop()
            .placeholder(loadingAnimation).into(binding.image)
        glide.load(viewModel.agencyLogoUrl).into(binding.agencyLogo)
    }

    fun onRecycled() {
        loadingAnimation.stop()
    }
}