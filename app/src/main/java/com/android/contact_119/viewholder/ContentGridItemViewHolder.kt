package com.android.contact_119.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.databinding.ItemContactRecyclerViewGridBinding

class ContentGridItemViewHolder(binding: ItemContactRecyclerViewGridBinding) :
    RecyclerView.ViewHolder(binding.root), ContactItemViewHolder {
        override val ivThumbnail = binding.ivTumbnail
        override val name = binding.tvItemTitle
        override val favoriteIcon = binding.ivFavorite
        override val favoriteButton = binding.btnFavorite
        override val itemView = binding.layoutItemView
}