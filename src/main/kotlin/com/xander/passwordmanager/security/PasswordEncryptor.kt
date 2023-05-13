package com.xander.passwordmanager.security

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Object to handle the encryption and decryption of the Passwords
 *
 * @author Xander Van der Weken
 */
object PasswordEncryptor {
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"

    private val key = SecretKeySpec("1234567890123456".toByteArray(), "AES")
    private val iv = IvParameterSpec(ByteArray(16))

    /**
     * Encrypt a Password
     *
     * @param password password to encrypt
     * @return String of the cipher password
     */
    fun encryptPassword(password: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(password.toByteArray())
        return Base64.getEncoder().encodeToString(cipherText)
    }

    /**
     * Decrypt a Password
     *
     * @param encryptedPassword encryptedPassword to decrypt
     * @return String of plaintext password
     */
    fun decryptPassword(encryptedPassword: String) : String {
        val cipher = Cipher.getInstance( ALGORITHM )
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val plainText = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword))
        return String(plainText)
    }
}