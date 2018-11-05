package com.archaic.firechaic.data.firebase.client

import android.util.Log
import com.archaic.firechaic.data.database.model.ChatRoom
import com.archaic.firechaic.data.database.model.User
import com.archaic.firechaic.data.firebase.ModelAdapter
import com.archaic.firechaic.data.firebase.REF_CHATROOM
import com.archaic.firechaic.data.firebase.REF_MESSAGE_LOG
import com.archaic.firechaic.data.firebase.REF_USER
import com.archaic.firechaic.data.firebase.callback.LoadItemCallback
import com.archaic.firechaic.data.firebase.callback.LoadMessageCallback
import com.archaic.firechaic.data.firebase.model.FireChatRoom
import com.archaic.firechaic.data.firebase.model.FireMessage
import com.archaic.firechaic.data.firebase.model.FireUser
import com.google.firebase.database.*

object DatabaseClient {

    const val TAG = "DatabaseClient"

    // For Singleton instantiation
    @Volatile
    private var instance: FirebaseDatabase? = null

    private fun getInstance(): FirebaseDatabase {
        return instance ?: synchronized(this) {
            instance ?: FirebaseDatabase.getInstance()
        }
    }

    fun loadSingleUser(userId: String, loadItemCallback: LoadItemCallback<User>) {
        val query: Query = getInstance()
            .reference.child(REF_USER).child(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
                loadItemCallback.onFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val fireUser = p0.getValue(FireUser::class.java)
                if (fireUser != null) {
                    val user = ModelAdapter.userFromFireUser(fireUser)
                    loadItemCallback.onLoaded(user)
                } else loadItemCallback.onFailed()
            }
        })
    }

    fun loadSingleChatRoom(chatRoomId: String, loadItemCallback: LoadItemCallback<ChatRoom>) {
        val query: Query = getInstance()
            .reference.child(REF_CHATROOM).child(chatRoomId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
                loadItemCallback.onFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val fireChatRoom = p0.getValue(FireChatRoom::class.java)
                if (fireChatRoom != null) {
                    val chatRoom = ModelAdapter.chatRoomFromFireChatRoom(fireChatRoom)
                    loadItemCallback.onLoaded(chatRoom)
                } else loadItemCallback.onFailed()
            }
        })
    }

    fun loadSingleMessage(messageId: String, myUserId: String, chatRoomId: String,
                          loadMessageCallback: LoadMessageCallback) {
        val query: Query = getInstance()
            .reference.child(REF_MESSAGE_LOG).child(chatRoomId).child(messageId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
                loadMessageCallback.onFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val fireMessage = p0.getValue(FireMessage::class.java)
                if (fireMessage != null) {
                    val action = FireMessage.FireMessageAction.valueOf(fireMessage.action)
                    if (action == FireMessage.FireMessageAction.DELETE) {
                        loadMessageCallback.onInterrupt(action, fireMessage.targetId!!)
                    } else {
                        val message = ModelAdapter.messageFromFireMessage(myUserId, chatRoomId, fireMessage)
                        loadMessageCallback.onLoaded(message)
                    }
                } else loadMessageCallback.onFailed()
            }
        })
    }
}