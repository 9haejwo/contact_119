package com.android.contact_119.adapter

import android.content.ClipData.Item
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.load
import com.android.contact_119.ContactDataListener
import com.android.contact_119.DialogFragment
import com.android.contact_119.R
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.ItemContactRecyclerViewBinding
import com.android.contact_119.databinding.ItemHeaderRecyclerViewBinding
import com.android.contact_119.fragment.ContactFragment
import com.android.contact_119.manager.ContactItemManager

private const val TYPE_HEADER = 0
private const val TYPE_CONTENT = 1

class ContactListAdapter(var items: MutableList<ContactItems>) :
    RecyclerView.Adapter<ViewHolder>() {

    var itemClick: ItemClick? = null

    interface ItemClick {
        fun onClick(list: MutableList<ContactItems>)
    }

    inner class HeaderViewHolder(binding: ItemHeaderRecyclerViewBinding) :
        ViewHolder(binding.root) {
        val location = binding.tvLocation
    }

    inner class ContentViewHolder(binding: ItemContactRecyclerViewBinding) :
        ViewHolder(binding.root) {
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
    ): ViewHolder {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
                ivThumbnail.setOnClickListener {
                    itemClick?.onClick(items)
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
}