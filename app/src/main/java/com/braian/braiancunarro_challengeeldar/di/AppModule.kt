package com.braian.braiancunarro_challengeeldar.di

import com.braian.braiancunarro_challengeeldar.data.api.ApiService
import com.braian.braiancunarro_challengeeldar.data.repository.LoginRepository
import com.braian.braiancunarro_challengeeldar.data.repository.LoginRepositoryImpl
import com.braian.braiancunarro_challengeeldar.domain.usecase.LoginUseCase
import com.braian.braiancunarro_challengeeldar.domain.usecase.LoginUseCaseImpl
import com.braian.braiancunarro_challengeeldar.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(AppComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiService()// Reemplaza esto con la instancia real de tu servicio API
    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCaseImpl(loginRepository)
    }

    @Provides
    fun provideLoginViewModel(loginUseCase: LoginUseCase): LoginViewModel {
        return LoginViewModel(loginUseCase)
    }

}
