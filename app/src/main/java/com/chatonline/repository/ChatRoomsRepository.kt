package com.chatonline.repository

import com.chatonline.model.room.ChatRoom

class ChatRoomsRepository {
    val rooms = listOf(
        ChatRoom(
            id = 0,
            latestMessage = null,
            name = "First",
            photoFileName = null,
            profileId = 0,
            type = 0,
            userPinned = false,
            userMuted = false,
            userExited = false,
            userBlocked = false,
            userChatRoomId = 0,
        ),
        ChatRoom(
            id = 1,
            latestMessage = null,
            name = "Second",
            photoFileName = null,
            profileId = 0,
            type = 0,
            userPinned = false,
            userMuted = false,
            userExited = false,
            userBlocked = false,
            userChatRoomId = 1,
        )
    )
    fun getAllChatRooms(): List<ChatRoom> {
        return rooms
    }
}