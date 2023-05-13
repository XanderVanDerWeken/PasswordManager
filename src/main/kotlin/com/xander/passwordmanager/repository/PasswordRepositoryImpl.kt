package com.xander.passwordmanager.repository

import com.xander.passwordmanager.persistence.PasswordEntry
import com.xander.passwordmanager.persistence.PasswordEntries
import com.xander.passwordmanager.security.PasswordEncryptor
import org.ktorm.database.Database
import org.ktorm.dsl.*

/**
 * Class implementing the Password Repository, for a Ktorm SQLite Database
 *
 * @see IPasswordRepository
 * @author Xander Van der Weken
 */
class PasswordRepositoryImpl(
    private val database: Database,
) : IPasswordRepository {

    /**
     * Method to add a new Password to the database, and will also encrypt the password
     *
     * @param username username to add
     * @param password password to add
     * @param platform platform to add
     */
    override fun addPassword(username: String, password: String, platform: String) {
        val encryptedPassword = PasswordEncryptor.encryptPassword(password)

        database.insertAndGenerateKey(PasswordEntries) {
            set(it.username, username)
            set(it.password, encryptedPassword)
            set(it.platform, platform)
        }
    }

    /**
     * Method to get a Password from the database, and will also decrypt the password
     *
     * @param platform platform from which to get
     * @param username username from which to get
     * @return String of found Password, or null if no password was found
     */
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