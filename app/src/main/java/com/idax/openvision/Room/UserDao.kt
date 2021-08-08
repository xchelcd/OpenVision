package com.idax.openvision.Room

import androidx.room.*
import com.idax.openvision.Entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): MutableList<User>

    @Query("SELECT * FROM user WHERE user.id = :userId")
    fun getUserById(userId: Int): User

    @Insert
    fun insertNewUser(user:User)

    @Delete
    fun deleteUser(user:User)

    @Update
    fun updateUser(user:User)
}