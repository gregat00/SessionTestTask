package com.example.sessiontesttask.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeStamp(
    @PrimaryKey
    val id: Long = 0,
    val time: Long,
)