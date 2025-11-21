package com.example.expensetrackerapp.di

import android.content.Context
import androidx.room.Room
import com.example.expensetrackerapp.framework.database.AppDatabase
import com.example.expensetrackerapp.framework.database.dao.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            klass = AppDatabase::class.java,
            name = "expense_database"
        ).build()
    }

    @Provides
    fun providesExpenseDao(database: AppDatabase): ExpenseDao {
        return database.expenseDao()
    }
}