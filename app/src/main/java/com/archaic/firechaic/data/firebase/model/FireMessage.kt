package com.archaic.firechaic.data.firebase.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName

data class FireMessage(

    @set:PropertyName("i")
    @get:PropertyName("i")
    var id: String,

    @set:PropertyName("g")
    @get:PropertyName("g")
    var targetId: String?,

    @set:PropertyName("a")
    @get:PropertyName("a")
    var action: String,

    @set:PropertyName("s")
    @get:PropertyName("s")
    var senderId: String,

    @set:PropertyName("y")
    @get:PropertyName("y")
    var type: String,

    @set:PropertyName("x")
    @get:PropertyName("x")
    var text: String,

    @set:PropertyName("m")
    @get:PropertyName("m")
    var mediaUrl: String?,

    @set:PropertyName("t")
    @get:PropertyName("t")
    var timestamp: Long
) {
    enum class FireMessageAction {
        CREATE,
        EDIT,
        DELETE
    }
}