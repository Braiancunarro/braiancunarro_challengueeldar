package com.braian.braiancunarro_challengeeldar.domain.usecase

import com.braian.braiancunarro_challengeeldar.data.repository.LoginRepository
import com.braian.braiancunarro_challengeeldar.domain.usecase.LoginUseCase

class LoginUseCaseImpl(private val loginRepository: LoginRepository) : LoginUseCase {

    override suspend fun execute(username: String, password: String): Result<Boolean> {
        // Aquí puedes realizar la lógica de autenticación real utilizando el LoginRepository

        // Ejemplo: Llamada al repositorio para realizar la autenticación
        return try {
            val loginResult = loginRepository.login(username, password)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
