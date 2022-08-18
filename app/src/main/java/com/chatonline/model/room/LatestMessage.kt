package com.chatonline.model.room

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LatestMessage(
    val dateSent: String,
    val id: Int,
    val messageType: Int?,
    val notReadCount: Int?,
    val notReceivedCount: Int?,
    val senderId: Int,
    val shortBody: String? = ".."
): Parcelable