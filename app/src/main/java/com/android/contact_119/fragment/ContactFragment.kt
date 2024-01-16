package com.android.contact_119.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.R
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.databinding.FragmentContactBinding
import com.android.contact_119.manager.ContactItemManager

class ContactFragment : Fragment() {
    private val binding by lazy { FragmentContactBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initToolbarLogo()
    }

    private fun initToolbarLogo() {
        binding.recyclerViewContact.initToolbarLogoWithScroll()
    }

    private fun RecyclerView.initToolbarLogoWithScroll() {
        val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        val logo = binding.ivToolbarLogo

        setOnScrollChangeListener { _, _, _, _, _ ->
            if (canScrollVertically(-1) && logo.visibility == View.GONE) {
                logo.apply {
                    startAnimation(fadeInAnim)
                    visibility = View.VISIBLE
                }
            }

            if (!canScrollVertically(-1)) {
                logo.apply {
                    clearAnimation()
                    visibility = View.GONE
                }
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.recyclerViewContact) {
            adapter = ContactListAdapter(ContactItemManager.sortWithHeader())
            layoutManager = LinearLayoutManager(context)
        }
    }
}