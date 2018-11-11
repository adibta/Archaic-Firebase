package com.archaic.firechaic.data

import com.archaic.firechaic.data.database.dao.ChatRoomDao
import com.archaic.firechaic.data.database.dao.MessageDao
import com.archaic.firechaic.data.database.dao.UserDao

class Repository private constructor(
    private val userDao: UserDao,
    private val chatRoomDao: ChatRoomDao,
    private val MessageDao: MessageDao
) {
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