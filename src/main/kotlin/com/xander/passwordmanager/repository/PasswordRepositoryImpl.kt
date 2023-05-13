package com.xander.passwordmanager.repository

import com.xander.passwordmanager.persistence.PasswordEntry
import com.xander.passwordmanager.persistence.PasswordEntries
import com.xander.passwordmanager.security.PasswordEncryptor
import org.ktorm.database.Database
import org.ktorm.dsl.*

class PasswordRepositoryImpl(
    private val database: Database,
) : IPasswordRepository {

    companion object {
        private val masterPassword: CharArray = "MasterPassword".toCharArray()
    }

    override fun addPassword(username: String, password: String, platform: String) {
        val encryptedPassword = PasswordEncryptor.encryptPassword(masterPassword, password).toString()

        database.insertAndGenerateKey(PasswordEntries) {
            set(it.username, username)
            set(it.password, encryptedPassword)
            set(it.platform, platform)
        }
    }

    override fun getPassword(platform: String, username: String): String? {
        val result = database.from(PasswordEntries)
            .select()
            .where{ (PasswordEntries.username eq username) and ( PasswordEntries.platform eq platform ) }
            .map {
                PasswordEntry(
                    id = it[PasswordEntries.id]!!.toLong(),
                    username = it[PasswordEntries.username]!!.toString(),
                    password = it[PasswordEntries.password]!!.toString(),
                    platform = it[PasswordEntries.platform]!!.toString()
                )
            }

        if(result.isNotEmpty()) {
            println("Is not empty")
            return PasswordEncryptor.decryptPassword(masterPassword, result.first().password.toByteArray())
        }
        println("Is empty")
        return null
    }
}