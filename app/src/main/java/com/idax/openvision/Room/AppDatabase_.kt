package com.idax.openvision.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.idax.openvision.Entity.User


@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class AppDatabase_ : RoomDatabase() {

    companion object {
        @Volatile private var instance: AppDatabase_? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase_::class.java, "open-vision.db")
            .build()
    }

    abstract fun userDao(): UserDao
}