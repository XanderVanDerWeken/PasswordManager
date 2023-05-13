package com.xander.passwordmanager.persistence

/**
 * Data Class for a row of a Password Entry
 *
 * @author Xander Van der Weken
 */
data class PasswordEntry(
    var id: Long? = null,
    val username: String,
    val password: String,
    val platform: String
)