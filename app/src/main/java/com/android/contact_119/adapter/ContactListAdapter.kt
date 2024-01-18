package com.android.contact_119.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.android.contact_119.R
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.ItemContactRecyclerViewBinding
import com.android.contact_119.databinding.ItemContactRecyclerViewGridBinding
import com.android.contact_119.databinding.ItemHeaderRecyclerViewBinding

const val TYPE_HEADER = 0
const val TYPE_CONTENT_LIST = 1
const val TYPE_CONTENT_GRID = 2


class ContactListAdapter(private val layoutManager: GridLayoutManager) :
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

    interface ContactViewHolder {
        val ivThumbnail: ImageView
        val name: TextView
        val favoriteIcon: ImageView
        val favoriteButton: ConstraintLayout
        val itemView: ConstraintLayout
    }

    inner class ContentViewHolder(binding: ItemContactRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root), ContactViewHolder {
        override val ivThumbnail = binding.ivTumbnail
        override val name = binding.tvItemTitle
        override val favoriteIcon = binding.ivFavorite
        override val favoriteButton = binding.btnFavorite
        override val itemView = binding.layoutItemView
    }

    inner class ContentGridViewHolder(binding: ItemContactRecyclerViewGridBinding) :
        RecyclerView.ViewHolder(binding.root), ContactViewHolder {
        override val ivThumbnail = binding.ivTumbnail
        override val name = binding.tvItemTitle
        override val favoriteIcon = binding.ivFavorite
        override val favoriteButton = binding.btnFavorite
        override val itemView = binding.layoutItemView
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContactItems.Header -> TYPE_HEADER
            is ContactItems.Contents -> {
                if (layoutManager.spanCount == 1) {
                    TYPE_CONTENT_LIST
                } else {
                    TYPE_CONTENT_GRID
                }
            }
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

            TYPE_CONTENT_LIST -> ContentViewHolder(
                ItemContactRecyclerViewBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_CONTENT_GRID -> ContentGridViewHolder(
                ItemContactRecyclerViewGridBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (item is ContactItems.Header) {
            with(holder as HeaderViewHolder) {
                location.text = item.location
            }
        }

        if (item is ContactItems.Contents) {
            with(holder as ContactViewHolder) {
                name.text = item.itemName
                ivThumbnail.setThumbnailImage(item.thumbnailImage)
                itemView.setOnClickListener {
                    itemClick?.onClick(item)
                }
                favoriteButton.setOnClickListener {
                    favoriteClick?.onFavoriteClick(item, position)
                }

                if (item.favorite) {
                    favoriteIcon.load(R.drawable.favorite_big_on)
                } else {
                    favoriteIcon.load(R.drawable.favorite_big_off)
                }
            }
        }
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
                return oldItem.ItemID == newItem.ItemID
            }

            override fun areContentsTheSame(oldItem: ContactItems, newItem: ContactItems): Boolean {
                return oldItem == newItem
            }
        }
    }
}


