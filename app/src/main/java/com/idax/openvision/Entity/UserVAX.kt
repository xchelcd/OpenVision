package com.idax.openvision.Entity

data class UserVAX(val id: Int, val name: String, val lastName: String) {

    override fun toString(): String {
        return "id=$id\nname='$name'\nlastName='$lastName'"
    }
}