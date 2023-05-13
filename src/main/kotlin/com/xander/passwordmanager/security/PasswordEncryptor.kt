package com.xander.passwordmanager.security

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object PasswordEncryptor {
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"

    private val key = SecretKeySpec("1234567890123456".toByteArray(), "AES")
    private val iv = IvParameterSpec(ByteArray(16))

    fun encryptPassword(password: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(password.toByteArray())
        return Base64.getEncoder().encodeToString(cipherText)
    }

    fun decryptPassword(encryptedPassword: String) : String {
        val cipher = Cipher.getInstance( ALGORITHM )
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val plainText = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword))
        return String(plainText)
    }
}