package com.android.pxapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.pxapp.data.model.Banner
import com.android.pxapp.databinding.ItemBannerBinding
import com.bumptech.glide.Glide

class BannerListAdapter(private val bannerClickListener :(Banner) -> Unit) : ListAdapter<Banner, BannerListAdapter.BannerViewHolder>(BannerDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = getItem(position)
        holder.bind(banner, bannerClickListener)

    }

    class BannerViewHolder(private val binding: ItemBannerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Banner, bannerClickListener: (Banner) -> Unit ) {
            binding.ivBanner.setOnClickListener {
                bannerClickListener(banner)
            }
            Glide.with(binding.root.context).load(banner.image).into(binding.ivBanner)

        }

        companion object {
            fun from(parent: ViewGroup): BannerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBannerBinding.inflate(layoutInflater, parent, false)
                return BannerViewHolder(binding)
            }

        }

    }

}

class BannerDiffCallback: DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.targetUrl == newItem.targetUrl
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }

}

