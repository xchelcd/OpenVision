package com.idax.openvision.Entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "user", indices = arrayOf(Index(value = ["email"],
    unique = true)))
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    var age: Int,
    var email: String = "None"
) : Serializable
/*
@Parcelize
data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val age: Int,
    val email: String = "None"
): Parcelable*/
