package com.android.contact_119.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.contact_119.R
import com.android.contact_119.databinding.FragmentMyPageBinding
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.data.ContactItems
import com.android.contact_119.manager.ContactItemManager
import com.android.contact_119.manager.UserManager
import com.android.contact_119.nowUser

class MyPageFragment : Fragment() {
    private val binding by lazy { FragmentMyPageBinding.inflate(layoutInflater) }
    private val layoutManager = GridLayoutManager(context, 1)
    private val listAdapter = ContactListAdapter(layoutManager)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUP()
    }

    private fun setUP() {
        initRecyclerView()
        initInput()
        updateView()
    }

    private fun initRecyclerView() {
        Log.i("my_page_test", "click")

        binding.mypageRecyclerview.adapter = listAdapter
        binding.mypageRecyclerview.layoutManager = layoutManager
        listAdapter.submitList(ContactItemManager.sortFavoriteWithHeader())
    }

    private fun initInput() {
        clickView()
        clickFavorite()
    }

    private fun updateView() {
        val user = UserManager.getUserByName(nowUser)

        with(binding) {
            tvUesrname.text = user.userName
            tvBloodtype.text = user.bloodType
            tvDetaillocate.text = user.location
            tvPhonenum.text = user.phoneNumber
        }
    }

    private fun clickView() {
        object : ContactListAdapter.ItemClick {
            override fun onClick(item: ContactItems) {
                val detailFragment = DetailFragment.newInstance(item.ItemID, nowUser)

                initRecyclerViewRefresher(detailFragment)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .addToBackStack("1")
                    .commit()
            }
        }.also { listAdapter.itemClick = it }
    }

    private fun clickFavorite() {
        object : ContactListAdapter.FavoriteClick {
            override fun onFavoriteClick(item: ContactItems, position: Int) {
                UserManager.registFavoriteItem(nowUser, item.ItemID)
                ContactItemManager.toggleFavorite(item.ItemID)
                listAdapter.submitList(ContactItemManager.sortFavoriteWithHeader().toList())
            }
        }.also { listAdapter.favoriteClick = it }
    }

    override fun onResume() {
        super.onResume()
        listAdapter.submitList(ContactItemManager.sortFavoriteWithHeader())
    }

    fun initRecyclerViewRefresher(fragment: DetailFragment) {
        object : DetailFragment.RefreshRecyclerView {
            override fun refreshRecycler(
                allList: MutableList<ContactItems>,
                favoriteList: MutableList<ContactItems>
            ) {
                listAdapter.submitList(favoriteList)
            }
        }.also {
            fragment.refresher = it
        }
    }
}