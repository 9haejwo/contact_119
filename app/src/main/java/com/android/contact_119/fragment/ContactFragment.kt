package com.android.contact_119.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.ContactDataListener
import com.android.contact_119.DetailFragment
import com.android.contact_119.DialogFragment
import com.android.contact_119.R
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.FragmentContactBinding
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

    private fun initRecyclerView() {
        with(binding.recyclerViewContact) {
            listAdapter.submitList(ContactItemManager.sortWithHeader())
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initToolbarLogo() {
        binding.recyclerViewContact.initToolbarLogoWithScroll()
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

    private fun initInput() {
        clickFavorite(listAdapter)
        clickView(listAdapter)
    }

    fun clickView(adapter: ContactListAdapter) {
        object : ContactListAdapter.ItemClick {
            override fun onClick(item: ContactItems) {
                val detailFragment = DetailFragment.newInstance(item.ItemID, nowUser) //아이템 아이디 유저이름
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }.also { adapter.itemClick = it }
    }

    fun clickFavorite(adapter: ContactListAdapter) {
        object : ContactListAdapter.FavoriteClick {
            override fun onFavoriteClick(item: ContactItems, position: Int) {
                UserManager.registFavoriteItem(nowUser, item.ItemID)
                ContactItemManager.checkFavorite(item.ItemID)
                listAdapter.submitList(ContactItemManager.sortWithHeader())
                Log.i("click_test", "${UserManager.getUserByName(nowUser)}")
            }
        }.also { adapter.favoriteClick = it }
    }

    private fun RecyclerView.initToolbarLogoWithScroll() {
        val logo = binding.ivToolbarLogo

        setOnScrollChangeListener { _, _, _, _, _ ->
            if (canScrollVertically(-1) && logo.visibility == gone) {
                logo.setVisible()
            }

            if (!canScrollVertically(-1) && logo.visibility == visible) {
                logo.setGone()
            }
        }
    }

    private fun View.setVisible() {
        val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in)

        startAnimation(fadeInAnim)
        visibility = visible
    }

    private fun View.setGone() {
        val fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fade_out)

        startAnimation(fadeOutAnim)
        visibility = gone
    }

    override fun onContactDataAdded(
        name: String,
        phoneNumber: String,
        address: String,
        location: String
    ) {
        ContactItemManager.addContent(name, phoneNumber, address, location)
        listAdapter.submitList(ContactItemManager.sortWithHeader().toList())
    }
}


