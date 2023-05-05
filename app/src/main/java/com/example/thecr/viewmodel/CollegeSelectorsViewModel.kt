package com.example.thecr.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecr.model.College
import com.example.thecr.model.Course
import com.example.thecr.repository.CollegeSelectorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollegeSelectorsViewModel @Inject constructor(private val collegeSelectorsRepository: CollegeSelectorsRepository) :
    ViewModel() {
    private val _colleges: MutableStateFlow<MutableList<College>> = MutableStateFlow(mutableStateListOf())
    val colleges: StateFlow<MutableList<College>> get() = _colleges

    private val _college: MutableStateFlow<College> = MutableStateFlow(College())
    val college: StateFlow<College> get() = _college

    fun getColleges() {
        viewModelScope.launch {
            _colleges.value = collegeSelectorsRepository.getColleges().toMutableList()
        }
    }

    fun onCollegeValueChanges(college: College) {
        _college.value = college
    }

    fun saveCollegeID(collegeId: String) {
        viewModelScope.launch {
            collegeSelectorsRepository.saveCollegeID(collegeId)
        }
    }

    val collegeID: StateFlow<String> = collegeSelectorsRepository.collegeID().filter {
        it.isNotEmpty()
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            "No saved data"
        )
}
