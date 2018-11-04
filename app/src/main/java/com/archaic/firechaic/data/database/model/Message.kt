package com.archaic.firechaic.data.database.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = ChatRoom::class, parentColumns = ["id"],
        childColumns = ["chat_room_id"],
        onDelete = CASCADE
    )],
    indices = [Index(value = ["id"])]
)
data class Message(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "chat_room_id") val chatRoomId: String,
    val type: MessageType,
    var text: String,
    @ColumnInfo(name = "media_url") val mediaUrl: String?,
    var status: MessageStatus,
    val timestamp: Long
) {

    enum class MessageType {
        MESSAGE,
        IMAGE
    }

    enum class MessageStatus {
        RECEIVED,
        PENDING,
        UNREAD,
        SENT
    }
}