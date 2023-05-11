package com.xander.passwordmanager.repository

import com.xander.passwordmanager.model.PasswordEntry
import com.xander.passwordmanager.persistence.PasswordEntries
import com.xander.passwordmanager.security.PasswordEncryptor
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf

class PasswordRepositoryImpl(
    private val database: Database,
) : IPasswordRepository {

    companion object {
        private val masterPassword: CharArray = "MasterPassword".toCharArray()
    }

    private val passwordTable = database.sequenceOf(PasswordEntries)

    override fun addPassword(username: String, password: String, platform: String) {
        TODO("Not yet implemented")
    }

    override fun getPassword(platform: String, username: String): String? {
        val query = database.from(PasswordEntries)
            .select()
            .where{ (PasswordEntries.username eq username) and ( PasswordEntries.platform eq platform ) }

        query.map { row -> PasswordEntry(row[PasswordEntries.id], ) }
    }
}