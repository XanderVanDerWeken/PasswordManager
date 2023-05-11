package com.xander.passwordmanager.repository

interface IPasswordRepository {
    fun addPassword(username: String, password: String, platform: String)
    fun getPassword(platform: String, username: String): String?
}