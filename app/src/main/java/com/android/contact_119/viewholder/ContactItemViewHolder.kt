package com.android.contact_119.viewholder

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

interface ContactItemViewHolder {
    val ivThumbnail: ImageView
    val name: TextView
    val favoriteIcon: ImageView
    val favoriteButton: ConstraintLayout
    val itemView: ConstraintLayout
}