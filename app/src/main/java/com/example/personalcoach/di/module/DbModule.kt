package com.example.personalcoach.di.module

import android.content.Context
import androidx.room.Room
import com.example.personalcoach.data.db.DbConstants
import com.example.personalcoach.data.db.PersonalCoachDb
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Binds
    @Singleton
    fun provideDatabase(context: Context): PersonalCoachDb {
        return Room.databaseBuilder(
            context.applicationContext, PersonalCoachDb::class.java,
            DbConstants.DATABASE_NAME
        )
            .build()
    }
}