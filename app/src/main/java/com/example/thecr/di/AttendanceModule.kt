package com.example.thecr.di

import com.example.thecr.repository.AttendanceRepository
import com.example.thecr.repository.EditRepository
import com.example.thecr.repository.impl.AttendanceRepositoryImpl
import com.example.thecr.repository.impl.EditRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
abstract class AttendanceModule {
    @Binds
    abstract fun provideAttendanceRepository(impl: AttendanceRepositoryImpl): AttendanceRepository
}