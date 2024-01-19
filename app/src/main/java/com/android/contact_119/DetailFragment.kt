package com.android.contact_119

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.FragmentDetailBinding
import com.android.contact_119.manager.ContactItemManager

private const val ARG_PARAM1 = "param1"  //아이템 아이디
private const val ARG_PARAM2 = "param2"  //user 이름

class DetailFragment : Fragment() {
    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    private var itemID: Long? = null
    private var nowUser: String? = null
    private var favoriteUser = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemID = it.getLong(ARG_PARAM1)
            nowUser = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        itemID?.let { id ->
            val contactItem = ContactItemManager.getById(id)

            binding.tvHospital.text = contactItem.itemName
            binding.tvPhoneNum.text = contactItem.phoneNumber
            binding.tvAddr.text = contactItem.address
            binding.ivDetail.setImageResource(contactItem.picture ?: R.drawable.hospital)
        }

        binding.detailLike.setOnClickListener {
            toggleFavorite()
        }


    }


    var refrecher: RefreshRecyclerView? = null

    private fun toggleFavorite() {
        if (favoriteUser) {
            binding.ivLike.setImageResource(R.drawable.favorite_big_off)
            favoriteUser = false
            ContactItemManager.checkFavorite(itemID ?: 0)
            refrecher?.refreshRecycler(ContactItemManager.sortAllWithHeader())
        } else {
            binding.ivLike.setImageResource(R.drawable.favorite_big_on)
            favoriteUser = true
            ContactItemManager.checkFavorite(itemID ?: 0)
            refrecher?.refreshRecycler(ContactItemManager.sortAllWithHeader())
        }
    }


    companion object {
        fun newInstance(param1: Long, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    interface RefreshRecyclerView {
        fun refreshRecycler(list: MutableList<ContactItems>)
    }
}