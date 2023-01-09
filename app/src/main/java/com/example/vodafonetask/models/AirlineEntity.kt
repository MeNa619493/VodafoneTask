package com.example.vodafonetask.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class AirLineEntity(
    @PrimaryKey
    val id: String,
    val established: String? = null,
    val country: String? = null,
    val website: String? = null,
    val name: String? = null,
    val headQuaters: String? = null,
    val logo: String? = null,
    val slogan: String? = null,
    val createdDate: String? = null,
    var textHighlight: String = ""
) : Parcelable {


    companion object {

        private fun toEntity(model: AirLineModel): AirLineEntity =
            AirLineEntity(
                model.id ?: "",
                model.established,
                model.country,
                model.website,
                model.name,
                model.headQuaters,
                model.logo,
                model.slogan,
                model.createdDate
            )

        fun toEntityList(data: List<AirLineModel>): List<AirLineEntity> {
            val list: ArrayList<AirLineEntity> = ArrayList()
            data.forEach { list.add(toEntity(it)) }
            return list
        }
    }
}



