package com.app.acronyms.di

import com.app.acronyms.data.AppRepository
import com.app.acronyms.data.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindingsModule {
    @Binds
    @Singleton
    fun bindsRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository
}