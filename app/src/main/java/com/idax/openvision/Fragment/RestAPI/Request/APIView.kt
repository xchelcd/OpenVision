package com.idax.openvision.Fragment.RestAPI.Request

import com.idax.openvision.Entity.UserVAX

interface APIView {
    fun onSuccess(user:UserVAX)
    fun onError(message:String)
}