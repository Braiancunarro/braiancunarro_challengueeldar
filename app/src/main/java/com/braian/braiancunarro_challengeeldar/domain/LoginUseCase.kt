package com.braian.braiancunarro_challengeeldar.domain

interface LoginUseCase {
    suspend fun execute(username: String, password: String): Result<Boolean>
}