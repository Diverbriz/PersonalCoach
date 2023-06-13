package com.example.personalcoach.data.db.dao

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type: List<T>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWithIgnoreStrategy(type: T): Long

    @Insert
    fun insertWithAbortStrategy(type: T): Long

    @Update
    fun update(type: T): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateWithIgnoreStrategy(type: T): Int

    @Delete
    fun delete(type: T)

    @RawQuery
    fun rawQuery(sqlQuery: SupportSQLiteQuery): Cursor
}