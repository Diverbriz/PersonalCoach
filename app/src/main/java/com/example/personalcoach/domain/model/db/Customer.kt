package com.example.personalcoach.domain.model.db

import androidx.room.*

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "gender")
    val gender: String?,
    @ColumnInfo(name = "email_id")
    val emailId: String?
)
