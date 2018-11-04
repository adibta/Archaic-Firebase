package com.archaic.firechaic.data.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["id"])]
)
data class User(
    @PrimaryKey() val id: String,
    var name: String,
    var profileUrl: String,
    var lastOnline: Long
) {

    override fun toString(): String {
        return name
    }
}