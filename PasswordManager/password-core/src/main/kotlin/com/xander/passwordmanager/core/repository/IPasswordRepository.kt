package com.xander.passwordmanager.core.repository

/**
 * Interface for the Password Repository, to work with the Storage.
 * It handles Methods to add and get Passwords.
 *
 * @author Xander Van der Weken
 */
interface IPasswordRepository {
    /**
     * Method to add a new Password to storage
     *
     * @param username username to add
     * @param password password to add
     * @param platform platform to add
     */
    fun addPassword(username: String, password: String, platform: String)

    /**
     * Method to get a Password from Storage
     *
     * @param platform platform from which to get
     * @param username username from which to get
     * @return String of found Password, or null if no password was found
     */
    fun getPassword(platform: String, username: String): String?
}