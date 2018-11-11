package com.archaic.firechaic.data

import androidx.paging.DataSource
import com.archaic.firechaic.data.database.dao.ChatRoomDao
import com.archaic.firechaic.data.database.dao.MessageDao
import com.archaic.firechaic.data.database.dao.UserDao
import com.archaic.firechaic.data.database.model.ChatRoom
import com.archaic.firechaic.data.database.model.Message
import com.archaic.firechaic.data.database.model.User

class Repository private constructor(
    private val userDao: UserDao,
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao
) {

    fun getChatRooms(): List<ChatRoom> {
        return chatRoomDao.getAll()
    }

    fun getChatRoomsPaged(): DataSource.Factory<Int, ChatRoom> {
        return chatRoomDao.getAllPaged()
    }

    fun getChatRoom(chatRoomId: String): ChatRoom {
        return chatRoomDao.getChatRoom(chatRoomId)
    }

    fun insertChatRooms(chatRoomList: List<ChatRoom>) {
        chatRoomDao.insertAll(chatRoomList)
    }

    fun insertChatRoom(chatRoom: ChatRoom) {
        chatRoomDao.insertChatRoom(chatRoom)
    }

    fun deleteChatRoom(chatRoom: ChatRoom) {
        chatRoomDao.deleteChatRoom(chatRoom)
    }

    fun clearChatRoom() {
        chatRoomDao.deleteAll()
    }

    fun getUsers(): List<User> {
        return userDao.getAll()
    }

    fun getUsersPaged(): DataSource.Factory<Int, User> {
        return userDao.getAllPaged()
    }

    fun getUser(userId: String): User {
        return userDao.getUser(userId)
    }

    fun insertUsers(userList: List<User>) {
        userDao.insertAll(userList)
    }

    fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    fun clearUser() {
        userDao.deleteAll()
    }

    fun getMessages(chatRoomId: String): List<Message> {
        return messageDao.getAll(chatRoomId)
    }

    fun getMessagesPaged(chatRoomId: String): DataSource.Factory<Int, Message> {
        return messageDao.getAllPaged(chatRoomId)
    }

    fun getMessage(messageId: String): Message {
        return messageDao.getMessage(messageId)
    }

    fun insertMessages(messageList: List<Message>) {
        messageDao.insertAll(messageList)
    }

    fun insertMessage(message: Message) {
        messageDao.insertMessage(message)
    }

    fun deleteMessage(message: Message) {
        messageDao.deleteMessage(message)
    }

    fun clearMessage() {
        messageDao.deleteAll()
    }

    companion object {

        private const val TAG = "REPOSITORY"

        // For Singleton instantiation
        @Volatile
        private var instance: Repository? = null

        fun getInstance(userDao: UserDao, chatRoomDao: ChatRoomDao, messageDao: MessageDao) =
            instance ?: synchronized(this) {
                instance
                    ?: Repository(userDao, chatRoomDao, messageDao).also { instance = it }
            }
    }
}