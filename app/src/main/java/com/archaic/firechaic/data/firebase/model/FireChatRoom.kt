package com.archaic.firechaic.data.firebase.model

import com.google.firebase.database.PropertyName

data class FireChatRoom(

    @set:PropertyName("i")
    @get:PropertyName("i")
    var id: String,

    @set:PropertyName("y")
    @get:PropertyName("y")
    var type: String,

    @set:PropertyName("n")
    @get:PropertyName("n")
    var name: String,

    @set:PropertyName("p")
    @get:PropertyName("p")
    var profileUrl: String,

    @set:PropertyName("x")
    @get:PropertyName("x")
    var lastMessage: String,

    @set:PropertyName("c")
    @get:PropertyName("c")
    var createdAt: Long,

    @set:PropertyName("l")
    @get:PropertyName("l")
    var lastOnline: Long,

    @set:PropertyName("r")
    @get:PropertyName("r")
    var member: List<String>
)