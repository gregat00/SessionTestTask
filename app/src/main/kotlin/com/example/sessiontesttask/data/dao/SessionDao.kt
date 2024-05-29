package com.example.sessiontesttask.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sessiontesttask.data.entities.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Query("SELECT * FROM session")
    fun getSession(): Flow<Session?>

    @Insert
    suspend fun insertSession(data: Session)

    @Query("UPDATE session SET count = count + 1 WHERE Id=0")
    suspend fun incrementSessionCount()
}