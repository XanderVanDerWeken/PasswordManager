package com.xander.passwordmanager.repository

import com.xander.passwordmanager.persistence.PasswordEntry
import com.xander.passwordmanager.persistence.PasswordEntries
import com.xander.passwordmanager.security.PasswordEncryptor
import org.ktorm.database.Database
import org.ktorm.dsl.*

class PasswordRepositoryImpl(
    private val database: Database,
) : IPasswordRepository {

    override fun addPassword(username: String, password: String, platform: String) {
        val encryptedPassword = PasswordEncryptor.encryptPassword(password)

        database.insertAndGenerateKey(PasswordEntries) {
            set(it.username, username)
            set(it.password, encryptedPassword)
            set(it.platform, platform)
        }
    }

    override fun getPassword(platform: String, username: String): String? {
        val query = database.from(PasswordEntries)
            .select()
            .where{ (PasswordEntries.username eq username) and ( PasswordEntries.platform eq platform ) }
        val result = query.map {
            PasswordEntry(
                id = it[PasswordEntries.id]!!,
                username = it[PasswordEntries.username]!!,
                platform = it[PasswordEntries.platform]!!,
                password = it[PasswordEntries.password]!!
            )
        }
        return if( result.isNotEmpty() ) {
            PasswordEncryptor.decryptPassword(
                result.first()
                    .password
            )
        }
        else {
            null
        }
    }
}