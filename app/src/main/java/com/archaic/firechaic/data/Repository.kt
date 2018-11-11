package com.archaic.firechaic.data

import android.util.Log
import androidx.paging.DataSource
import com.archaic.firechaic.data.database.dao.ChatRoomDao
import com.archaic.firechaic.data.database.dao.MessageDao
import com.archaic.firechaic.data.database.dao.UserDao
import com.archaic.firechaic.data.database.model.ChatRoom
import com.archaic.firechaic.data.database.model.Message
import com.archaic.firechaic.data.database.model.User
import com.archaic.firechaic.data.firebase.callback.LoadItemCallback
import com.archaic.firechaic.data.firebase.callback.LoadMessageCallback
import com.archaic.firechaic.data.firebase.client.DatabaseClient
import com.archaic.firechaic.data.firebase.model.FireMessage

class Repository private constructor(
    private val userDao: UserDao,
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao
) {

    fun loadUserChatRoom(userId: String) {
        DatabaseClient.loadUserChatroom(userId, object : LoadItemCallback<List<ChatRoom>> {
            override fun onLoaded(item: List<ChatRoom>) {
                insertChatRooms(item)
            }

            override fun onFailed() {
                Log.d(TAG, "LoadUserChatRoom: Failed")
            }
        })
    }

    fun loadUser(userId: String) {
        DatabaseClient.loadSingleUser(userId, object : LoadItemCallback<User> {
            override fun onLoaded(item: User) {
                insertUser(item)
            }

            override fun onFailed() {
                Log.d(TAG, "LoadUser: Failed")
            }
        })
    }

    fun loadMessages(userId: String, chatRoomId: String) {
        DatabaseClient.loadAllMessage(userId, chatRoomId, object : LoadMessageCallback {
            override fun onLoaded(message: Message) {
                messageDao.insertMessage(message)
            }

            override fun onInterrupt(action: FireMessage.FireMessageAction, messageId: String) {
                if (action == FireMessage.FireMessageAction.DELETE) {
                    messageDao.deleteMessage(messageDao.getMessage(messageId))
                }
            }

            override fun onFailed() {
                Log.d(TAG, "LoadMessages: Failed")
            }

        })
    }

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