package com.archaic.firechaic.data.firebase.client

import android.util.Log
import com.archaic.firechaic.data.database.model.ChatRoom
import com.archaic.firechaic.data.database.model.User
import com.archaic.firechaic.data.firebase.*
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
        val query: Query = getInstance().reference.child(REF_USER).child(userId)
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

    fun loadAllUser(loadItemCallback: LoadItemCallback<List<User>>) {
        val query: Query = getInstance().reference.child(REF_USER)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
                loadItemCallback.onFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val userList: MutableList<User> = mutableListOf()
                for (childSnapshot in p0.children) {
                    val fireUser = p0.getValue(FireUser::class.java)
                    if (fireUser != null) {
                        val user = ModelAdapter.userFromFireUser(fireUser)
                        userList.add(user)
                    }
                }
                if (userList.size > 0) loadItemCallback.onLoaded(userList)
                else loadItemCallback.onFailed()
            }
        })
    }

    private fun loadUserChatRoomId(userId: String, loadItemCallback: LoadItemCallback<List<String>>) {
        val query: Query = getInstance().reference.child(REF_USER_CHATROOM).child(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
                loadItemCallback.onFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val chatRoomIdList: MutableList<String> = mutableListOf()
                for (childSnapshot in p0.children) {
                    val chatRoomId = childSnapshot.key
                    if (chatRoomId != null) {
                        chatRoomIdList.add(chatRoomId)
                    }
                }
                if (chatRoomIdList.size > 0) loadItemCallback.onLoaded(chatRoomIdList)
                else loadItemCallback.onFailed()
            }
        })
    }

    fun loadSingleChatRoom(chatRoomId: String, loadItemCallback: LoadItemCallback<ChatRoom>) {
        val query: Query = getInstance().reference.child(REF_CHATROOM).child(chatRoomId)
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

    private fun loadMultipleChatRoom(counter: Int, chatRoomIdList: List<String>, chatRoomList: MutableList<ChatRoom>,
                                     loadItemCallback: LoadItemCallback<List<ChatRoom>>) {
        loadSingleChatRoom(chatRoomIdList[counter], object : LoadItemCallback<ChatRoom> {
            override fun onLoaded(item: ChatRoom) {
                chatRoomList.add(item)
                if (counter >= chatRoomIdList.size) {
                    if (chatRoomList.size > 0) loadItemCallback.onLoaded(chatRoomList)
                    else loadItemCallback.onFailed()
                } else loadMultipleChatRoom(counter + 1, chatRoomIdList, chatRoomList, loadItemCallback)
            }

            override fun onFailed() {
                if (counter >= chatRoomIdList.size) {
                    if (chatRoomList.size > 0) loadItemCallback.onLoaded(chatRoomList)
                    else loadItemCallback.onFailed()
                } else loadMultipleChatRoom(counter + 1, chatRoomIdList, chatRoomList, loadItemCallback)
            }

        })
    }

    fun loadUserChatroom(userId: String, loadItemCallback: LoadItemCallback<List<ChatRoom>>) {
        loadUserChatRoomId(userId, object : LoadItemCallback<List<String>> {
            override fun onLoaded(item: List<String>) {
                loadMultipleChatRoom(0, item, mutableListOf(),
                    object : LoadItemCallback<List<ChatRoom>> {
                        override fun onLoaded(item: List<ChatRoom>) {
                            loadItemCallback.onLoaded(item)
                        }

                        override fun onFailed() {
                            loadItemCallback.onFailed()
                        }
                    })
            }

            override fun onFailed() {
                loadItemCallback.onFailed()
            }
        })
    }

    fun loadAllChatroom(loadItemCallback: LoadItemCallback<List<ChatRoom>>) {
        val query: Query = getInstance().reference.child(REF_CHATROOM)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
                loadItemCallback.onFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val chatRoomList: MutableList<ChatRoom> = mutableListOf()
                for (childSnapshot in p0.children) {
                    val fireChatRoom = p0.getValue(FireChatRoom::class.java)
                    if (fireChatRoom != null) {
                        val chatRoom = ModelAdapter.chatRoomFromFireChatRoom(fireChatRoom)
                        chatRoomList.add(chatRoom)
                    }
                }
                if (chatRoomList.size > 0) loadItemCallback.onLoaded(chatRoomList)
                else loadItemCallback.onFailed()
            }
        })
    }

    fun loadSingleMessage(
        messageId: String, myUserId: String, chatRoomId: String,
        loadMessageCallback: LoadMessageCallback
    ) {
        val query: Query = getInstance().reference.child(REF_MESSAGE_LOG).child(chatRoomId).child(messageId)
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

    fun loadAllMessage(myUserId: String, chatRoomId: String, loadMessageCallback: LoadMessageCallback) {
        val query: Query = getInstance().reference.child(REF_MESSAGE_LOG).child(chatRoomId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
                loadMessageCallback.onFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (childSnapshot in p0.children) {
                    val fireMessage = p0.getValue(FireMessage::class.java)
                    if (fireMessage != null) {
                        val action = FireMessage.FireMessageAction.valueOf(fireMessage.action)
                        if (action == FireMessage.FireMessageAction.DELETE) {
                            loadMessageCallback.onInterrupt(action, fireMessage.targetId!!)
                        } else {
                            val message = ModelAdapter
                                .messageFromFireMessage(myUserId, chatRoomId, fireMessage)
                            loadMessageCallback.onLoaded(message)
                        }
                    }
                }
            }
        })
    }
}