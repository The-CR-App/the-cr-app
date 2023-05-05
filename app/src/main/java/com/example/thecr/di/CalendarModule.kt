package com.example.thecr.di

import com.example.thecr.repository.CalendarRepository
import com.example.thecr.repository.impl.CalendarRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CalendarModule {
    @Binds
    abstract fun provideCalendarRepository(impl:CalendarRepositoryImpl):CalendarRepository
}