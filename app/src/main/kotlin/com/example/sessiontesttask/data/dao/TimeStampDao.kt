package com.example.sessiontesttask.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.sessiontesttask.data.entities.TimeStamp

@Dao
interface TimeStampDao {
    @Query("SELECT * FROM timestamp")
    fun getTimeStamp(): TimeStamp?

    @Upsert
    suspend fun insertTimeStamp(data: TimeStamp)
}