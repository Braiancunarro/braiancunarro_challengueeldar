package com.braian.braiancunarro_challengeeldar.utils

import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

class EncryptionUtils {
    private val algorithm = "AES"
    private val transformation = "AES/ECB/PKCS5Padding"

    // Clave secreta para cifrar y descifrar. Asegúrate de cambiar esto y almacenarlo de manera segura.
    private val secretKey = "TuClaveSecreta"

    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, generateKey())
        val encryptedBytes = cipher.doFinal(text.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    fun decrypt(text: String): String {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, generateKey())
        val encryptedBytes = Base64.decode(text, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

    private fun generateKey(): Key {
        return SecretKeySpec(secretKey.toByteArray(), algorithm)
    }
}
