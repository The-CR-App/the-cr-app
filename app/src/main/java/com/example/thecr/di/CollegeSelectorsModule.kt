package com.example.thecr.di

import com.example.thecr.repository.CollegeSelectorsRepository
import com.example.thecr.repository.impl.CollegeSelectorsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
abstract class CollegeSelectorsModule {
    @Binds
    abstract fun provideCollegeSelectors(impl: CollegeSelectorsRepositoryImpl): CollegeSelectorsRepository
}