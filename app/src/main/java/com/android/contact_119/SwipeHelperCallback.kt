package com.android.contact_119

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.data.ContactItems
import com.android.contact_119.manager.ContactItemManager

class SwipeHelperCallback(private val context: Context, private var adapter: ContactListAdapter,private val recyclerView: RecyclerView) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, LEFT or RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val item = adapter.getPosition(position) as? ContactItems.Contents
        //오른쪽 스와이프 시 전화연결
        if(direction == ItemTouchHelper.RIGHT) {
            item?.let {
                val phoneNumber = it.phoneNumber
                Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                    context.startActivity(this)
                }
            }
            //왼쪽 스와이프 시 아이템 삭제
        }else{
            item?.let {
                ContactItemManager.contactItems.remove(item)
                recyclerView.adapter = ContactListAdapter(ContactItemManager.sortWithHeader()) }
        }
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.5f
    }

}
