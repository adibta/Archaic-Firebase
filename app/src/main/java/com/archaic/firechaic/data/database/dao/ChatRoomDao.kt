package com.archaic.firechaic.data.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.archaic.firechaic.data.database.model.ChatRoom

@Dao
interface ChatRoomDao {

    @Query("SELECT * FROM chat_rooms ORDER BY timestamp DESC")
    fun getAllPaged(): DataSource.Factory<Int, ChatRoom>

    @Query("SELECT * FROM chat_rooms ORDER BY timestamp DESC")
    fun getAll(): List<ChatRoom>

    @Query("SELECT * FROM chat_rooms WHERE id = :id LIMIT 1")
    fun getChatRoom(id: String): ChatRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChatRoom(chatRoom: ChatRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(chatRoomList: List<ChatRoom>)

    @Delete
    fun deleteChatRoom(chatRoom: ChatRoom)

    @Query("DELETE FROM chat_rooms")
    fun deleteAll()
}