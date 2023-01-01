package com.example.vodafonetask.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Airline (
    @PrimaryKey
    val id: Double,
    val name: String? = null,
    val country: String? = null,
    val logo: String? = null,
    val slogan: String? = null,

    @SerializedName("head_quaters")
    val headQuaters: String? = null,

    val website: String? = null,
    val established: String? = null
): Parcelable