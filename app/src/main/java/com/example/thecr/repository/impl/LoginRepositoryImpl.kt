package com.example.thecr.repository.impl

import android.app.Application
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thecr.MainActivity
import com.example.thecr.TheCRApp
import com.example.thecr.model.College
import com.example.thecr.model.Teacher
import com.example.thecr.navigation.TheCRScreen
import com.example.thecr.repository.LoginRepository
import com.example.thecr.util.readString
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val context: MainActivity,
    val appContext: Application
) : LoginRepository {
    private val COLLEGE_KEY = "college_id"
    override val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val success: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var verificationOtp: String = ""
    var resentToken: PhoneAuthProvider.ForceResendingToken? = null
    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()
    override val hasUser: Boolean
        get() = firebaseAuth.currentUser != null
    override val currentUser: Flow<String>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                auth.currentUser?.uid?.let { this.trySend(it) }
            }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }

    fun collegeID(): Flow<String> = appContext.readString(COLLEGE_KEY)

    private val authCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object :
        PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(
            credential:
            PhoneAuthCredential
        ) {
            Log.d("Hello", credential.smsCode.toString())
            loading.value = false
            signInWithAuthCredential(credential)
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            when (exception) {
                is FirebaseAuthInvalidCredentialsException -> {


                }
            }
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, token)
            verificationOtp = verificationId
            resentToken = token
            loading.value = true
        }

    }

    override suspend fun authenticate(phoneNumber: String) {
        val option =
            PhoneAuthOptions
                .newBuilder(firebaseAuth)
                .setPhoneNumber("+91$phoneNumber")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(context)
                .setCallbacks(authCallback).build()

        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    override suspend fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        authCallback.onCodeSent(verificationId, token)
    }

    override suspend fun onVerifyOtp(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp, code)
        signInWithAuthCredential(credential)
    }

    override fun signInWithAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firestore.collection("Colleges").document("iZvUMwonFLLq6Qpy7aEm").collection("Teachers").document(currentUserId).set(Teacher())
                success.value = true
            } else {
                Log.e("Firebase Auth Error", task.exception?.message.toString())
            }
        }
    }


    override suspend fun onVerificationCompleted(credential: PhoneAuthCredential) {
        authCallback.onVerificationCompleted(credential)
    }

    override suspend fun onVerificationFailed(exception: Exception) {
        authCallback.onVerificationFailed(exception as FirebaseException)
    }

    override suspend fun getUserPhone(): String {
        return firebaseAuth.currentUser?.phoneNumber.orEmpty()
    }
}