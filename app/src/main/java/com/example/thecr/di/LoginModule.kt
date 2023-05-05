package com.example.thecr.di

import com.example.thecr.repository.LoginRepository
import com.example.thecr.repository.impl.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginModule {
    @Binds abstract fun provideLoginRepository(impl:LoginRepositoryImpl):LoginRepository
}