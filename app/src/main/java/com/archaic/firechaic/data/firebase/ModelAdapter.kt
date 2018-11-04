package com.archaic.firechaic.data.firebase

import com.archaic.firechaic.data.database.model.ChatRoom
import com.archaic.firechaic.data.database.model.Message
import com.archaic.firechaic.data.database.model.User
import com.archaic.firechaic.data.firebase.model.FireChatRoom
import com.archaic.firechaic.data.firebase.model.FireMessage
import com.archaic.firechaic.data.firebase.model.FireUser

object ModelAdapter {

    fun userFromFireUser(fireUser: FireUser): User {
        return User(
            fireUser.id,
            fireUser.name,
            fireUser.profileUrl,
            fireUser.lastOnline
        )
    }

    fun chatRoomFromFireChatRoom(fireChatRoom: FireChatRoom): ChatRoom {
        return ChatRoom(
            fireChatRoom.id,
            fireChatRoom.name,
            fireChatRoom.profileUrl,
            fireChatRoom.lastMessage,
            fireChatRoom.createdAt
        )
    }

    fun messageFromFireMessage(userId: String, chatRoomId: String, fireMessage: FireMessage): Message {
        return Message(
            fireMessage.targetId ?: fireMessage.id,
            chatRoomId,
            Message.MessageType.valueOf(fireMessage.type),
            fireMessage.text,
            fireMessage.mediaUrl,
            if (fireMessage.senderId == userId) Message.MessageStatus.SENT else Message.MessageStatus.RECEIVED,
            fireMessage.timestamp
        )
    }
}