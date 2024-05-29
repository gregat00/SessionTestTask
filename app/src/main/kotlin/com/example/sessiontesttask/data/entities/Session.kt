package com.example.sessiontesttask.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    @PrimaryKey
    val id: Long = 0,
    val count: Int = 0,
)