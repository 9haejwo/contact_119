package com.android.contact_119.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.ContactDataListener
import com.android.contact_119.DialogFragment
import com.android.contact_119.R
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.FragmentContactBinding
import com.android.contact_119.manager.ContactItemManager

class ContactFragment : Fragment(), ContactDataListener {
    private val binding by lazy { FragmentContactBinding.inflate(layoutInflater) }
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
    }

    private fun initRecyclerView() {
        with(binding.recyclerViewContact) {
            adapter = ContactListAdapter(ContactItemManager.sortWithHeader())
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

    private fun RecyclerView.initToolbarLogoWithScroll() {
        val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        val logo = binding.ivToolbarLogo

        setOnScrollChangeListener { _, _, _, _, _ ->
            if (canScrollVertically(-1) && logo.visibility == View.GONE) {
                logo.run {
                    startAnimation(fadeInAnim)
                    visibility = View.VISIBLE
                }
            }

            if (!canScrollVertically(-1)) {
                logo.run {
                    clearAnimation()
                    visibility = View.GONE
                }
            }
        }
    }

    override fun onContactDataAdded(item: ContactItems.Contents) {
        ContactItemManager.addContent(item)
        binding.recyclerViewContact.run {
            adapter = ContactListAdapter(ContactItemManager.sortWithHeader())
            adapter?.notifyDataSetChanged()
        }
    }
}


