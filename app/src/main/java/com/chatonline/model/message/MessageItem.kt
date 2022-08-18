package com.chatonline.model.message


data class MessageItem(
    val authorId: Int?,
    val body: String?,
    val dateDeleted: String?,
    val dateRead: String?,
    val dateReceived: String?,
    val dateSent: String,
    val dateStarred: String?,
    val file: File?,
    val fileId: Int?,
    val id: Int,
    val linkedId: Int?,
    val messageTag: MessageTag?,
    val messageTagId: Int?,
    val messageType: Int?,
    val reaction: Int?,
    val receiverId: Int?,
    val senderId: Int,
    val senderName: String,
    val chatRoomId: Int
)

data class MessageItemDto(
    private val body: String,
    private val dateSent: String,
    private val senderId: Int,
    private val linkedId: Int? = null,
    private val messageTag: MessageTagDto?
)