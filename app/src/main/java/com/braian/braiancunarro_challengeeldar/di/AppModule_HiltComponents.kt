package com.braian.braiancunarro_challengeeldar.di

import com.braian.braiancunarro_challengeeldar.domain.LoginUseCase
import com.braian.braiancunarro_challengeeldar.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AppModule_HiltComponents {

    @Provides
    fun provideLoginViewModel(loginUseCase: LoginUseCase): LoginViewModel {
        return LoginViewModel(loginUseCase)
    }
}
