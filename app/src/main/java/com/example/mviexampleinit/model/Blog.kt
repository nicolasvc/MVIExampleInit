package com.example.mviexampleinit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Blog(
    @Expose
    @SerializedName("pk")
    val pk:Int,
    @Expose
    @SerializedName("title")
    val title:String? = null ,
    @Expose
    @SerializedName("body")
    val body:String? = null ,
    @Expose
    @SerializedName("image")
    val image:String? = null ,
) {
    override fun toString(): String {
        return "Blog(pk=$pk, title=$title, body=$body, image=$image)"
    }
}