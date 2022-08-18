package com.chatonline.model.room

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatRoom(
    val id: Int,
    val latestMessage: LatestMessage?,
    val name: String,
    val photoFileName: String?,
    val profileId: Int,
    val type: Int,
    val userBlocked: Boolean?,
    val userChatRoomId: Int,
    val userExited: Boolean?,
    val userMuted: Boolean?,
    val userPinned: Boolean?
) : Parcelable