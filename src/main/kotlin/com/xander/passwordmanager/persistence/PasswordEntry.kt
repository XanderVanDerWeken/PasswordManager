package com.xander.passwordmanager.persistence


data class PasswordEntry(
    var id: Long? = null,
    val username: String,
    val password: String,
    val platform: String
)