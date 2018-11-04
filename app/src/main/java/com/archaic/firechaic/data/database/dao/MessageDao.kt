package com.archaic.firechaic.data.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.archaic.firechaic.data.database.model.Message

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages WHERE chat_room_id = :chatRoomId ORDER BY timestamp DESC")
    fun getAllPaged(chatRoomId: String): DataSource.Factory<Int, Message>

    @Query("SELECT * FROM messages WHERE chat_room_id = :chatRoomId ORDER BY timestamp DESC")
    fun getAll(chatRoomId: String): List<Message>

    @Query("SELECT * FROM messages where id = :id LIMIT 1")
    fun getMessage(id: String): Message

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messageList: List<Message>)

    @Delete
    fun deleteMessage(message: Message)

    @Query("DELETE FROM messages")
    fun deleteAll()
}