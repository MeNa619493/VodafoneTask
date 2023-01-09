package com.example.vodafonetask.models

import com.google.gson.annotations.SerializedName

data class AirLineModel(
    @field:SerializedName("id")
    val id: String?=null,

    @field:SerializedName("established")
    val established: String?=null,

    @field:SerializedName("country")
    val country: String?=null,

    @field:SerializedName("website")
    val website: String?=null,

    @field:SerializedName("name")
    val name: String?=null,

    @field:SerializedName("head_quaters")
    val headQuaters: String?=null,

    @field:SerializedName("logo")
    val logo: String?=null,

    @field:SerializedName("slogan")
    val slogan: String?=null,

    @field:SerializedName("createdDate")
    val createdDate: String?=null
)