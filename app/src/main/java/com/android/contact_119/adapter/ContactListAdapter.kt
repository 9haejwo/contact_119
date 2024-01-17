package com.android.contact_119.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.android.contact_119.R
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.ItemContactRecyclerViewBinding
import com.android.contact_119.databinding.ItemHeaderRecyclerViewBinding
import com.android.contact_119.manager.UserManager
import com.android.contact_119.nowUser

private const val TYPE_HEADER = 0
private const val TYPE_CONTENT = 1

class ContactListAdapter(var items: MutableList<ContactItems>) :
    ListAdapter<ContactItems, RecyclerView.ViewHolder>(diffUtil) {
    var itemClick: ItemClick? = null
    var favoriteClick: FavoriteClick? = null

    interface ItemClick {
        fun onClick(item: ContactItems)
    }

    interface FavoriteClick {
        fun onFavoriteClick(item: ContactItems, position: Int)
    }

    inner class HeaderViewHolder(binding: ItemHeaderRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val location = binding.tvLocation
    }

    inner class ContentViewHolder(binding: ItemContactRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivThumbnail = binding.ivTumbnail
        val name = binding.tvItemTitle
        val favoriteIcon = binding.ivFavorite
        val favoriteButton = binding.btnFavorite
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ContactItems.Header -> TYPE_HEADER
            is ContactItems.Contents -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                ItemHeaderRecyclerViewBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_CONTENT -> ContentViewHolder(
                ItemContactRecyclerViewBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (item is ContactItems.Header) {
            with(holder as HeaderViewHolder) {
                location.text = item.location
            }
        }

        if (item is ContactItems.Contents) {
            with(holder as ContentViewHolder) {
                name.text = item.itemName
                ivThumbnail.setThumbnailImage(item.thumbnailImage)
                itemView.setOnClickListener {
                    itemClick?.onClick(item)
                }
                favoriteButton.setOnClickListener {
                    favoriteClick?.onFavoriteClick(item, position)
                }

                Log.i("click_test", "${UserManager.getUserByName(nowUser).favorite}")

                if (UserManager.getUserByName(nowUser).favorite.contains(item)) {
                    Log.i("click_test", "드루와")
                    favoriteIcon.load(R.drawable.favorite_big_on)
                } else {
                    favoriteIcon.load(R.drawable.favorite_big_off)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun ImageView.setThumbnailImage(image: Int?) {
        if (image == null) {
            load(R.drawable.main_symbol)
            return
        }
        load(image)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ContactItems>() {
            override fun areItemsTheSame(oldItem: ContactItems, newItem: ContactItems): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ContactItems, newItem: ContactItems): Boolean {
                return oldItem == newItem
            }
        }
    }
}