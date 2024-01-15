package com.android.contact_119

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.android.contact_119.databinding.FragmentDialogBinding


class DialogFragment : DialogFragment() {
    private lateinit var binding:FragmentDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogBinding.inflate(inflater)
        return binding.root
    }
}