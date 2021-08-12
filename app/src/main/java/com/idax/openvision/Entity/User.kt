package com.idax.openvision.Entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "user", indices = arrayOf(Index(value = ["email"],
    unique = true)))
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    var age: Int,
    var email: String = "None",

    ) : Serializable

data class User_(val name: String, val lastName: String, val height: Float) : Serializable
/*
@Parcelize
data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val age: Int,
    val email: String = "None"
): Parcelable*/
