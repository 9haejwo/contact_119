package com.android.contact_119.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import coil.api.load
import com.android.contact_119.R
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.FragmentDetailBinding
import com.android.contact_119.manager.ContactItemManager

private const val ARG_PARAM1 = "param1"  //아이템 아이디
private const val ARG_PARAM2 = "param2"  //user 이름

class DetailFragment : Fragment() {
    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    private var itemID: Long? = null
    private var nowUser: String? = null
    private lateinit var nowItem: ContactItems.Contents
    var refresher: RefreshRecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemID = it.getLong(ARG_PARAM1)
            nowUser = it.getString(ARG_PARAM2)
        }
        nowItem = ContactItemManager.getById(itemID ?: 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUP()
    }

    private fun setUP() {
        updateItem()
    }

    private fun updateItem() {
        with(binding) {
            tvHospital.text = nowItem.itemName
            tvPhoneNum.text = nowItem.phoneNumber
            tvAddr.text = nowItem.address
            ivLike.checkFavorite(nowItem)
            ivDetail.load(nowItem.picture ?: R.drawable.hospital)
            detailLike.setOnClickListener { toggleFavorite() }
        }
    }

    private fun ImageView.checkFavorite(item: ContactItems.Contents) {
        load(if (item.favorite) R.drawable.favorite_big_on else R.drawable.favorite_big_off)
    }

    private fun toggleFavorite() {
        if (nowItem.favorite) {
            binding.ivLike.setFavorite(R.drawable.favorite_big_off)
        } else {
            binding.ivLike.setFavorite(R.drawable.favorite_big_on)
        }
    }

    private fun ImageView.setFavorite(resource: Int) {
        load(resource)
        ContactItemManager.toggleFavorite(nowItem.ItemID)
        refresher?.refreshRecycler(
            ContactItemManager.sortAllWithHeader(),
            ContactItemManager.sortFavoriteWithHeader(),
        )
    }

    companion object {
        fun newInstance(param1: Long, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    interface RefreshRecyclerView {
        fun refreshRecycler(
            allList: MutableList<ContactItems>,
            favoriteList: MutableList<ContactItems>,
        )
    }
}