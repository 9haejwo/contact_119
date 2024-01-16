package com.android.contact_119.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.fragment.app.Fragment
import com.android.contact_119.DialogFragment
=======
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.contact_119.R
import com.android.contact_119.adapter.ContactListAdapter
>>>>>>> d04ca78c76b7ca4caaa373be6b13c149809bfea1
import com.android.contact_119.databinding.FragmentContactBinding
import com.android.contact_119.manager.ContactItemManager

class ContactFragment : Fragment() {
    private val binding by lazy { FragmentContactBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
<<<<<<< HEAD
        binding.btnAdd.setOnClickListener {
            DialogFragment().show(requireActivity().supportFragmentManager,"AddContactDialog")
=======
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding.recyclerViewContact) {
            adapter = ContactListAdapter(ContactItemManager.getAllItems())
            layoutManager = LinearLayoutManager(context)
>>>>>>> d04ca78c76b7ca4caaa373be6b13c149809bfea1
        }
    }
}