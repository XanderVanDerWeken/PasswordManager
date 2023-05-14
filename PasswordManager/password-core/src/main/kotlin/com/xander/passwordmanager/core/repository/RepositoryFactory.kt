package com.xander.passwordmanager.core.repository

import com.xander.passwordmanager.core.persistence.SqliteDatabase
import org.ktorm.database.Database

object RepositoryFactory {
    private val database: Database

    init {
        this.database = SqliteDatabase.database
    }

    fun create() : IPasswordRepository {
        return PasswordRepositoryImpl(database)
    }
}