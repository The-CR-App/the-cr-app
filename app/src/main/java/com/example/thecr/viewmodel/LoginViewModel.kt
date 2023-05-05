package com.example.thecr.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecr.model.College
import com.example.thecr.repository.LoginRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository,val firestore: FirebaseFirestore):ViewModel() {

    private val _phoneNumber: MutableState<String> = mutableStateOf("")
    val number: MutableState<String> get()=_phoneNumber
    val loading = loginRepository.loading
    val success = loginRepository.success

    private val _code: MutableStateFlow<String> = MutableStateFlow("")
    val code: StateFlow<String> get() = _code

    fun authenticatePhone(phone: String) {
        viewModelScope.launch {
            loginRepository.authenticate(phone)
        }
    }

    fun onNumberChange(number: String) {
        _phoneNumber.value = number
    }

    fun onCodeChange(code: String) {
        _code.value = code.take(6)
    }

    fun verifyOtp(code: String) {
        viewModelScope.launch {
            loginRepository.onVerifyOtp(code)
        }
    }

}