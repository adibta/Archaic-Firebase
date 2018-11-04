package com.archaic.firechaic.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.archaic.firechaic.data.database.dao.ChatRoomDao
import com.archaic.firechaic.data.database.dao.MessageDao
import com.archaic.firechaic.data.database.dao.UserDao
import com.archaic.firechaic.data.database.model.ChatRoom
import com.archaic.firechaic.data.database.model.Message
import com.archaic.firechaic.data.database.model.User

@Database(entities = [User::class, ChatRoom::class, Message::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun ChatRoomDao(): ChatRoomDao
    abstract fun Message(): MessageDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}