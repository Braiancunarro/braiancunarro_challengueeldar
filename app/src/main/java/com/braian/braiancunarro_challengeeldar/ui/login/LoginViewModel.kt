package com.braian.braiancunarro_challengeeldar.ui.login

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braian.braiancunarro_challengeeldar.MainActivity
import com.braian.braiancunarro_challengeeldar.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    var username: String = ""
    var password: String = ""

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun onLoginButtonClick() {
        login(username, password)

    }

    private fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase.execute(username, password)
            _loginResult.value = result.isSuccess

        }
    }
}

