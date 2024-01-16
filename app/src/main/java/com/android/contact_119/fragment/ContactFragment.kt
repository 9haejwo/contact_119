package com.android.contact_119.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.contact_119.ContactDataListener
import com.android.contact_119.DialogFragment
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.FragmentContactBinding
import com.android.contact_119.manager.ContactItemManager

class ContactFragment : Fragment() ,ContactDataListener{
    private val binding by lazy { FragmentContactBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.btnAdd.setOnClickListener {
            val dialogFragment = DialogFragment()
            dialogFragment.setContactDataListener(this)
            dialogFragment.show(requireActivity().supportFragmentManager, "AddContactDialog")
        }
    }

    private fun initRecyclerView() {
        val listAdapter = ContactListAdapter(ContactItemManager.getAllItems())
        with(binding.recyclerViewContact) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)

        }
    }

    override fun onContactDataAdded(item: ContactItems.Contents) {
        (binding.recyclerViewContact.adapter as? ContactListAdapter)?.items?.add(item)
        binding.recyclerViewContact.adapter?.notifyDataSetChanged()
    }
}