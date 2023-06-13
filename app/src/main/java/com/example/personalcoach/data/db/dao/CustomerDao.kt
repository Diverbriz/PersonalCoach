package com.example.personalcoach.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.personalcoach.data.db.entity.UserEntity
import com.example.personalcoach.domain.model.db.Customer

@Dao
abstract class UserDao : BaseDao<UserEntity> {

    @get:Query(value = "SELECT * FROM user")
    abstract val allUsers: LiveData<List<UserEntity>>


}