package com.example.storyapp.Network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RegisterUser(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginUser(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class LoginResult(

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("userId")
    var userId: String,

    @field:SerializedName("token")
    var token: String,
) : Parcelable

data class ListStory(

    @field:SerializedName("listStory")
    val listStory: List<ListStoryResult>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Entity(tableName = "story")
@Parcelize
data class ListStoryResult(

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lon")
    var longitude: Double,

    @field:SerializedName("lat")
    var latitude: Double,
) : Parcelable

data class AddStoryResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)