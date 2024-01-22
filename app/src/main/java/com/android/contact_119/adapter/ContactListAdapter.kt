package com.android.contact_119.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import coil.api.load
import com.android.contact_119.R
import com.android.contact_119.data.ContactItems
import com.android.contact_119.viewholder.ContactItemViewHolder
import com.android.contact_119.viewholder.ContentGridItemViewHolder as GridViewHolder
import com.android.contact_119.viewholder.ContentsViewHolder as ListViewHolder
import com.android.contact_119.viewholder.HeaderViewHolder
import com.android.contact_119.manager.ContactItemManager

const val TYPE_HEADER = 0
const val TYPE_CONTENT_LIST = 1
const val TYPE_CONTENT_GRID = 2


class ContactListAdapter(private val layoutManager: GridLayoutManager) :
    ListAdapter<ContactItems, ViewHolder>(diffUtil) {
    var itemClick: ItemClick? = null
    var favoriteClick: FavoriteClick? = null

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

    interface ItemClick {
        fun onClick(item: ContactItems)
    }

    interface FavoriteClick {
        fun onFavoriteClick(item: ContactItems, position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val span = layoutManager.spanCount

        if (item is ContactItems.Header) {
            return TYPE_HEADER
        }

        if (item is ContactItems.Contents && span == 1) {
            return TYPE_CONTENT_LIST
        }

        if (item is ContactItems.Contents && span > 1) {
            return TYPE_CONTENT_GRID
        }
        throw IllegalArgumentException("Invaild span count")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(inflater.inflateBinding(parent))
            TYPE_CONTENT_LIST -> ListViewHolder(inflater.inflateBinding(parent))
            TYPE_CONTENT_GRID -> GridViewHolder(inflater.inflateBinding(parent))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        if (item is ContactItems.Header) {
            with(holder as HeaderViewHolder) {
                location.text = item.location
            }
        }

        if (item is ContactItems.Contents) {
            with(holder as ContactItemViewHolder) {
                name.text = item.itemName
                ivThumbnail.setThumbnailImage(item.thumbnailImage)
                favoriteIcon.checkFavorite(item)
                itemView.setOnClickListener { itemClick?.onClick(item) }
                favoriteButton.setOnClickListener { favoriteClick?.onFavoriteClick(item, position) }
            }
        }
    }

    private inline fun < reified T : ViewBinding> LayoutInflater.inflateBinding(parent: ViewGroup): T {
        val inflate = T::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return inflate.invoke(null, this, parent, false) as T
    }

    private fun ImageView.setThumbnailImage(image: Int?) {
        load(image ?: R.drawable.main_symbol)
    }

    private fun ImageView.checkFavorite(item: ContactItems.Contents) {
        load(if (item.favorite) R.drawable.favorite_big_on else R.drawable.favorite_big_off)
    }

    fun getPosition(position: Int): ContactItems {
        return ContactItemManager.sortAllWithHeader()[position]
    }
}
