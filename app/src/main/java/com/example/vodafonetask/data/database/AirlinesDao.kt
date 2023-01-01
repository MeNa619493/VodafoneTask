package com.example.vodafonetask.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vodafonetask.models.Airline
import kotlinx.coroutines.flow.Flow

@Dao
interface AirlinesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAirlines(airlines: List<Airline>)

    @Query("SELECT * FROM airline")
    fun getAllAirlines(): Flow<List<Airline>>
}