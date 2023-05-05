package com.example.thecr.di

import com.example.thecr.repository.EditRepository
import com.example.thecr.repository.ExportRepository
import com.example.thecr.repository.impl.EditRepositoryImpl
import com.example.thecr.repository.impl.ExportRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
abstract class ExportModule {
    @Binds
    abstract fun provideExportRepository(impl: ExportRepositoryImpl): ExportRepository
}