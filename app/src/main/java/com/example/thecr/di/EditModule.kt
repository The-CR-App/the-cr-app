package com.example.thecr.di

import com.example.thecr.model.Teacher
import com.example.thecr.repository.EditRepository
import com.example.thecr.repository.LoginRepository
import com.example.thecr.repository.impl.EditRepositoryImpl
import com.example.thecr.repository.impl.LoginRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await

@Module
@InstallIn(ViewModelComponent::class,SingletonComponent::class)
abstract class EditModule {
    @Binds
    abstract fun provideEditRepository(impl: EditRepositoryImpl): EditRepository
}