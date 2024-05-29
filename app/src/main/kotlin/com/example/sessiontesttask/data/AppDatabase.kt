package com.example.sessiontesttask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sessiontesttask.data.dao.SessionDao
import com.example.sessiontesttask.data.dao.TimeStampDao
import com.example.sessiontesttask.data.entities.Session
import com.example.sessiontesttask.data.entities.TimeStamp

@Database(entities = [Session::class, TimeStamp::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun timeStampDao(): TimeStampDao
}