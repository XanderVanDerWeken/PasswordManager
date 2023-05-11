package com.xander.passwordmanager.persistence

import org.ktorm.database.Database

object DatabaseFactory {
    fun create() : Database {
        val jdbcUrl = "jdbc:sqlite::memory:"
        val database = Database.connect(jdbcUrl)

        return database
    }
}