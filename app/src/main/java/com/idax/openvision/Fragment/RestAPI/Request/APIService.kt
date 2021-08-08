package com.idax.openvision.Fragment.RestAPI.Request

import com.idax.openvision.Entity.UserVAX
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @POST
    fun getUsersById(@Field("id") id:Int): Call<UserVAX>

}