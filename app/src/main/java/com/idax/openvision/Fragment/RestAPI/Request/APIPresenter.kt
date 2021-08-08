package com.idax.openvision.Fragment.RestAPI.Request

import com.idax.openvision.Entity.UserVAX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIPresenter(private val view: APIView) {

    fun getUserById(id: Int) {

        RetrofitClient.instance.getUsersById(id)
            .enqueue(object : Callback<UserVAX>{
                override fun onResponse(call: Call<UserVAX>, response: Response<UserVAX>) {
                    response.body()?.let { view.onSuccess(it) }
                }

                override fun onFailure(call: Call<UserVAX>, t: Throwable) {
                    view.onError(t.message!!)
                }

            })

    }
}