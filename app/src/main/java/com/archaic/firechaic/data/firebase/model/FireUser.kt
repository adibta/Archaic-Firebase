package com.archaic.firechaic.data.firebase.model

import com.google.firebase.database.PropertyName

data class FireUser(

    @set:PropertyName("i")
    @get:PropertyName("i")
    var id: String,

    @set:PropertyName("n")
    @get:PropertyName("n")
    var name: String,

    @set:PropertyName("p")
    @get:PropertyName("p")
    var profileUrl: String,


    @set:PropertyName("l")
    @get:PropertyName("l")
    var lastOnline: Long
)