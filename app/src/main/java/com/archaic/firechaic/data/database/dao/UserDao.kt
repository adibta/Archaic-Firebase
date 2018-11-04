package com.archaic.firechaic.data.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.archaic.firechaic.data.database.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllPaged(): DataSource.Factory<Int, User>

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users where id = :id LIMIT 1")
    fun getUser(id: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userList: List<User>)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()
}