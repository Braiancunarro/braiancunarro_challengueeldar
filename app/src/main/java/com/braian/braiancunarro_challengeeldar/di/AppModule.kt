package com.braian.braiancunarro_challengeeldar.di

import com.braian.braiancunarro_challengeeldar.data.api.ApiService
import com.braian.braiancunarro_challengeeldar.data.repository.LoginRepository
import com.braian.braiancunarro_challengeeldar.data.repository.LoginRepositoryImpl
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
        return ApiService()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl()
    }


}
