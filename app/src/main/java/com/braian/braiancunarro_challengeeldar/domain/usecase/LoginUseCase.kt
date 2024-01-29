package com.braian.braiancunarro_challengeeldar.domain.usecase

interface LoginUseCase {
    suspend fun execute(username: String, password: String): Result<Boolean>
}