package com.archaic.firechaic.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "chat_rooms",
    indices = [Index(value = ["last_message_id"])]
)
data class ChatRoom(
    @PrimaryKey val id: String,
    var name: String,
    @ColumnInfo(name = "profile_url") var profileUrl: String,
    @ColumnInfo(name = "last_message_id") var lastMessageId: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {
    override fun toString(): String {
        return name
    }
}