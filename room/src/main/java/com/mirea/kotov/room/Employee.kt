package com.mirea.kotov.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var id: Int?,
    @ColumnInfo var name: String?,
    @ColumnInfo var salary: Int?
)