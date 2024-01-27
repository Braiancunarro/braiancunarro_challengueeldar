package com.braian.braiancunarro_challengeeldar.data.repository

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<Boolean>
}