package com.android.contact_119.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.android.contact_119.ContactDataListener
import com.android.contact_119.DetailFragment
import com.android.contact_119.DialogFragment
import com.android.contact_119.R
import com.android.contact_119.SwipeHelperCallback
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
    private val listAdapter by lazy { ContactListAdapter(gridLayoutManager) }
    private val gridLayoutManager = GridLayoutManager(context, 1)
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val myPageRefresher: DetailFragment.RefreshRecyclerView? = null

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

    private fun initRecyclerView() {
        with(binding.recyclerViewContact) {
            itemAnimator = null
            layoutManager = gridLayoutManager
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addSwipeAction(context)
        }
    }

    private fun addSwipeAction(context: Context) {
        val swipeHelperCallback = SwipeHelperCallback(context, listAdapter, binding.recyclerViewContact)
        itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewContact)
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
        clickLayoutChange()
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
                listAdapter.submitList(ContactItemManager.sortAllWithHeader().toList())
                myPageRefresher?.refreshRecycler(ContactItemManager.sortAllWithHeader(), ContactItemManager.sortFavoriteWithHeader())
            }
        }.also { listAdapter.favoriteClick = it }
    }

    private fun clickLayoutChange() {
        binding.btnLayoutChange.setOnClickListener {
            convertSpanCount(it as ImageView)
        }
    }

    private fun convertSpanCount(btn: ImageView) {
        with(gridLayoutManager) {
            if (spanCount == 1) {
                itemTouchHelper.attachToRecyclerView(null)
                spanCount = 3
                btn.setVisibleFadeIn()
//                setGridLayoutHeder()
                btn.load(R.drawable.list_icon)
            } else {
                spanCount = 1
                btn.setVisibleFadeIn()
                btn.load(R.drawable.grid_icon)
                itemTouchHelper.attachToRecyclerView(binding.recyclerViewContact)
            }
        }
        binding.recyclerViewContact.layoutManager = gridLayoutManager
    }

    private fun setGridLayoutHeder() {
        Log.i("span_size_test", "click")
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 3 else 1
            }
        }
    }

    fun initRecyclerViewRefresher(fragment: DetailFragment) {
        object : DetailFragment.RefreshRecyclerView {
            override fun refreshRecycler(
                allList: MutableList<ContactItems>,
                favoriteList: MutableList<ContactItems>
            ) {
                listAdapter.submitList(allList)
            }
        }.also { fragment.refresher = it }
    }

    override fun onResume() {
        super.onResume()
        listAdapter.submitList(ContactItemManager.sortAllWithHeader())
    }
}