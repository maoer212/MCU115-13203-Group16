package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _progress = MutableStateFlow(0)
    val progress: StateFlow<Int> = _progress.asStateFlow()

    private val _progressStatus = MutableStateFlow("Idle")
    val progressStatus: StateFlow<String> = _progressStatus.asStateFlow()

    private val _resultStatus = MutableStateFlow("Idle")
    val resultStatus: StateFlow<String> = _resultStatus.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private var job: Job? = null

    fun startTask() {
        if (_isRunning.value) return

        job?.cancel()
        _isRunning.value = true
        _progress.value = 0

        job = viewModelScope.launch {
            // 1. Preparation phase ("Preparing...")
            _progressStatus.value = "Preparing..."
            _resultStatus.value = "Preparing..."
            delay(1500)

            // 2. Working phase ("Working..." / "Working... X%")
            _resultStatus.value = "Working..."
            for (i in 1..100) {
                delay(50)
                _progress.value = i
                _progressStatus.value = "Working... $i%"
            }

            // 3. Finished phase ("Task Finished")
            _progressStatus.value = "Task Finished"
            _resultStatus.value = "Task Finished"
            _isRunning.value = false
        }
    }

    fun cancelTask() {
        job?.cancel()
        job = null
        _isRunning.value = false
        _progress.value = 0
        _progressStatus.value = "Idle"
        _resultStatus.value = "Idle"
    }
}