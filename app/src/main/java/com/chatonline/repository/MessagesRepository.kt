package com.chatonline.repository

import com.chatonline.model.message.MessageItem


class MessagesRepository {
    val listOfMessages = mutableListOf(
        MessageItem(
            id = 0,
            authorId = 1,
            body = "This is a message",
            dateSent = "00:00",
            dateDeleted = null,
            dateRead = null,
            dateReceived = "00:00",
            file = null,
            fileId = null,
            dateStarred = null,
            senderId = 0,
            senderName = "Lionel",
            messageType = 0,
            messageTag = null,
            messageTagId = null,
            reaction = null,
            linkedId = 0,
            receiverId = 1,
            chatRoomId = 0
        ),
        MessageItem(
            id = 1,
            authorId = 1,
            body = "This is a another message",
            dateSent = "00:00",
            dateDeleted = null,
            dateRead = null,
            dateReceived = "00:00",
            file = null,
            fileId = null,
            dateStarred = null,
            senderId = 0,
            senderName = "Lionel",
            messageType = 0,
            messageTag = null,
            messageTagId = null,
            reaction = null,
            linkedId = 0,
            receiverId = 1,
            chatRoomId = 0
        ),
        MessageItem(
            id = 2,
            authorId = 1,
            body = "This is a another message",
            dateSent = "00:00",
            dateDeleted = null,
            dateRead = null,
            dateReceived = "00:00",
            file = null,
            fileId = null,
            dateStarred = null,
            senderId = 0,
            senderName = "Lionel",
            messageType = 0,
            messageTag = null,
            messageTagId = null,
            reaction = null,
            linkedId = 0,
            receiverId = 1,
            chatRoomId = 1
        ),
        MessageItem(
            id = 3,
            authorId = 1,
            body = "This is a another message",
            dateSent = "00:00",
            dateDeleted = null,
            dateRead = null,
            dateReceived = "00:00",
            file = null,
            fileId = null,
            dateStarred = null,
            senderId = 0,
            senderName = "Lionel",
            messageType = 0,
            messageTag = null,
            messageTagId = null,
            reaction = null,
            linkedId = 0,
            receiverId = 1,
            chatRoomId = 1
        )
    )

    fun getAllMessages(chatRoomId: Int): List<MessageItem> {
        return listOfMessages.filter { it.chatRoomId==chatRoomId }
    }
    fun postMessage(body: String){
        val message = MessageItem(
            id = 0,
            authorId = 1,
            body = body,
            dateSent = "00:00",
            dateDeleted = null,
            dateRead = null,
            dateReceived = "00:00",
            file = null,
            fileId = null,
            dateStarred = null,
            senderId = 0,
            senderName = "Lionel",
            messageType = 0,
            messageTag = null,
            messageTagId = null,
            reaction = null,
            linkedId = 0,
            receiverId = 1,
            chatRoomId = 0
        )

        listOfMessages.add(message)
    }
}