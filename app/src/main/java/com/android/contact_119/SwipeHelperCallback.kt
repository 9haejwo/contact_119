package com.android.contact_119

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.adapter.ContactListAdapter
import com.android.contact_119.adapter.TYPE_HEADER
import com.android.contact_119.data.ContactItems
import com.android.contact_119.manager.ContactItemManager

class SwipeHelperCallback(
    private val context: Context,
    private var adapter: ContactListAdapter,
) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (viewHolder.itemViewType == TYPE_HEADER) {
            return makeMovementFlags(0, 0)
        }
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
        adapter.notifyDataSetChanged()
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
        val logoImage = ImageView(context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            val width = LayoutParams.MATCH_PARENT
            val height = 500
            layoutParams = LinearLayout.LayoutParams(width, height).apply {
                topMargin = 100
            }
            adjustViewBounds = true
            item.thumbnailImage?.let { setImageResource(it) }

        }
        val contactText = TextView(context).apply {
            textSize = 30f
            gravity = Gravity.CENTER
            item.phoneNumber.let { text = it }
        }
        val messageText = TextView(context).apply {
            textSize = 20f
            gravity = Gravity.CENTER
            layoutParams =
                LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    .apply {
                        topMargin = 100
                        bottomMargin = 50
                    }
            text = "연락처를 삭제 하시겠습니까?"
        }
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(logoImage)
            addView(contactText)
            addView(messageText)
        }

        AlertDialog.Builder(context).apply {
            setView(layout)
            setPositiveButton("확인") { _, _ ->
                item.let {
                    ContactItemManager.contactItems.remove(item)
                    adapter.submitList(ContactItemManager.sortAllWithHeader())
                }
            }
            setNegativeButton("취소") { _, _ ->
                adapter.notifyDataSetChanged()
            }
            show()
        }
    }
}
