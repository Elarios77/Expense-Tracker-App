package com.example.expensetrackerapp.di

import com.example.expensetrackerapp.data.ExpenseRepositoryImpl
import com.example.expensetrackerapp.domain.repository.ExpensesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(impl: ExpenseRepositoryImpl): ExpensesRepository = impl
}