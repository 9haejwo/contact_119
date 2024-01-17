package com.android.contact_119

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.contact_119.databinding.FragmentDetailBinding
import com.android.contact_119.manager.ContactItemManager

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailFragment : Fragment() {
    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    private var param1: Long? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getLong(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivDetail.setOnClickListener {
            Log.i("click_test", "${ContactItemManager.getById(param1 ?: 0)}, ${param2}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Long, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}