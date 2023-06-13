package com.example.personalcoach.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.personalcoach.data.db.dao.UserDao
import com.example.personalcoach.data.db.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = DbConstants.DATABASE_VERSION
)
abstract class PersonalCoachDb: RoomDatabase() {
    abstract val userDao: UserDao
}