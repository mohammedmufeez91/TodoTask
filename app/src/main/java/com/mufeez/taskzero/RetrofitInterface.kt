package com.mufeez.taskzero

import com.google.gson.JsonObject

import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    @FormUrlEncoded
    @POST("todoTask/todozero.php?")
    fun login(
            @Field("module") module: String,
            @Field("action") action: String,
            @Field("username") username: String,
            @Field("email") email: String,
            @Field("photourl") photourl: String
    ): Call<String>

}