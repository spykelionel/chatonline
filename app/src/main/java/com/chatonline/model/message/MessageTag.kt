package com.chatonline.model.message

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageTag(
    val chatRoomId: Int,
    val creatorId: Int? ,
    val dateCreated: String? ,
    val id: Int? ,
    val name: String ,
    val parentId: Int?
): Parcelable

@Parcelize
data class MessageTagDto(
    val chatRoomId: Int,
): Parcelable