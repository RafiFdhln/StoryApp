package com.example.storyapp.Network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface   ApiService {
    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<RegisterUser>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") pass: String
    ): Call<LoginUser>

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") auth: String,
        @Query("page") page : Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ) : ListStory

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null
    ): Call<AddStoryResponse>
}