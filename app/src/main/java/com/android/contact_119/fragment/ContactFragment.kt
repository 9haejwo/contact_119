package com.android.contact_119.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.contact_119.DialogFragment
import com.android.contact_119.databinding.FragmentContactBinding

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
        binding.btnAdd.setOnClickListener {
            DialogFragment().show(requireActivity().supportFragmentManager,"AddContactDialog")
        }
    }
}