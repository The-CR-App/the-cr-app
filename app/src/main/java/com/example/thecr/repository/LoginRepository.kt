package com.example.thecr.repository

import com.example.thecr.model.Teacher
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LoginRepository {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<String>
    suspend fun authenticate(phoneNumber: String)
    suspend fun onCodeSent(
        verificationId: String,
        token:
        PhoneAuthProvider.ForceResendingToken
    )

    suspend fun onVerifyOtp(code: String)

    suspend fun onVerificationCompleted(
        credential: PhoneAuthCredential
    )

    suspend fun onVerificationFailed(exception: Exception)
    suspend fun getUserPhone(): String
    fun signInWithAuthCredential(credential: PhoneAuthCredential)
    val loading: MutableStateFlow<Boolean>
    val success: MutableStateFlow<Boolean>
}