package com.android.contact_119.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.ContactDataListener
import com.android.contact_119.DetailFragment
import com.android.contact_119.DialogFragment
import com.android.contact_119.R
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.FragmentContactBinding
import com.android.contact_119.extensions.setGoneFadeOut
import com.android.contact_119.extensions.setVisibleFadeIn
import com.android.contact_119.manager.ContactItemManager
import com.android.contact_119.manager.UserManager
import com.android.contact_119.nowUser

const val gone = View.GONE
const val visible = View.VISIBLE

class ContactFragment : Fragment(), ContactDataListener {
    private val binding by lazy { FragmentContactBinding.inflate(layoutInflater) }
    private val listAdapter by lazy { ContactListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        initRecyclerView()
        initToolbarLogo()
        initDialog()
        initInput()
    }

    var spanSize = 3
    val gridLayoutManager = GridLayoutManager(context, spanSize)
    private fun initRecyclerView() {
        with(binding.recyclerViewContact) {
            itemAnimator = null
            layoutManager = GridLayoutManager(context, 1)
//            layoutManager = gridLayoutManager.also {
//                it.spanSizeLookup = object : SpanSizeLookup() {
//                    override fun getSpanSize(position: Int): Int = when (position) {
//                        0 -> spanSize
//                        else -> 1
//                    }
//                }
//            }
            adapter = listAdapter.apply {
                submitList(ContactItemManager.sortAllWithHeader())
            }
        }
    }

    private fun initToolbarLogo() {
        binding.recyclerViewContact.initToolbarLogoWithScroll()
    }

    private fun RecyclerView.initToolbarLogoWithScroll() {
        val logo = binding.ivToolbarLogo

        this.setOnScrollChangeListener { _, _, _, _, _ ->
            if (canScrollVertically(-1) && logo.visibility == gone) {
                logo.setVisibleFadeIn()
            }

            if (!canScrollVertically(-1) && logo.visibility == visible) {
                logo.setGoneFadeOut()
            }
        }
    }

    private fun initDialog() {
        binding.btnAdd.setOnClickListener {
            DialogFragment().apply {
                setContactDataListener(this@ContactFragment)
            }.also {
                it.show(requireActivity().supportFragmentManager, "AddContactDialog")
            }
        }
    }

    override fun onContactDataAdded(
        name: String,
        phoneNumber: String,
        address: String,
        location: String
    ) {
        ContactItemManager.addContent(name, phoneNumber, address, location)
        listAdapter.submitList(ContactItemManager.sortAllWithHeader().toList())
    }

    private fun initInput() {
        clickView()
        clickFavorite()
    }

    fun clickView() {
        object : ContactListAdapter.ItemClick {
            override fun onClick(item: ContactItems) {
                val detailFragment = DetailFragment.newInstance(item.ItemID, nowUser)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }.also { listAdapter.itemClick = it }
    }

    fun clickFavorite() {
        object : ContactListAdapter.FavoriteClick {
            override fun onFavoriteClick(item: ContactItems, position: Int) {
                UserManager.registFavoriteItem(nowUser, item.ItemID)
                ContactItemManager.checkFavorite(item.ItemID)
                listAdapter.submitList(ContactItemManager.sortAllWithHeader())
            }
        }.also { listAdapter.favoriteClick = it }
    }
}