package com.xander.passwordmanager.security

import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object PasswordEncryptor {
    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val IV_LENGTH = 12
    private const val TAG_LENGTH = 128
    private const val SALT_LENGTH = 16
    private const val ITERATION_COUNT = 65536
    private const val KEY_LENGTH = 256
    private const val SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256"

    private val random = SecureRandom()

    fun encryptPassword(masterPassword: CharArray, password: String): ByteArray {
        val salt = ByteArray(SALT_LENGTH)
        random.nextBytes(salt)

        val spec: KeySpec = PBEKeySpec(masterPassword, salt, ITERATION_COUNT, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
        val secretKey = SecretKeySpec(factory.generateSecret(spec).encoded, "AES")

        val iv = ByteArray(IV_LENGTH)
        random.nextBytes(iv)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH, iv))

        val encryptedPassword = cipher.doFinal( password.toByteArray( Charsets.UTF_8 ) )

        return salt + iv + encryptedPassword
    }

    fun decryptPassword(masterPassword: CharArray, encryptedPassword: ByteArray) : String {
        val salt = encryptedPassword.copyOfRange(0, SALT_LENGTH)
        val iv = encryptedPassword.copyOfRange(SALT_LENGTH, SALT_LENGTH + IV_LENGTH)
        val data = encryptedPassword.copyOfRange(SALT_LENGTH + IV_LENGTH, encryptedPassword.size)

        val spec: KeySpec = PBEKeySpec(masterPassword, salt, ITERATION_COUNT, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
        val secretKey = SecretKeySpec(factory.generateSecret(spec).encoded, "AES")

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH, iv))

        val decryptedPassword = cipher.doFinal(data)

        return decryptedPassword.toString(Charsets.UTF_8)
    }
}