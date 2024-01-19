package com.android.contact_119

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.data.ContactItems
import com.android.contact_119.manager.ContactItemManager

class SwipeHelperCallback(
    private val context: Context,
    private var adapter: ContactListAdapter,
    private val recyclerView: RecyclerView
) :
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
        val item = adapter.getPosition(position) as ContactItems.Contents

        //오른쪽 스와이프 시 전화연결 //왼쪽 스와이프 시 아이템 삭제
        if (direction == ItemTouchHelper.RIGHT) callConnect(item) else removeContact(item)



    }
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 20
    }

    private fun callConnect(item: ContactItems.Contents) {
        item.let {
            val phoneNumber = it.phoneNumber
            Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
                context.startActivity(this)
            }
        }
    }
    private fun removeContact(item: ContactItems.Contents) {
        AlertDialog.Builder(context).apply {
            setMessage("연락처를 삭제 하시겠습니까?")
            setPositiveButton("확인") { _, _ ->
                item.let {
                    ContactItemManager.contactItems.remove(item)
                    recyclerView.adapter =
                        ContactListAdapter(ContactItemManager.sortWithHeader())
                }
            }
            setNegativeButton("취소") { _, _ ->
                recyclerView.adapter =
                    ContactListAdapter(ContactItemManager.sortWithHeader())
            }
            show()
        }
    }
}
