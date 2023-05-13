package com.xander.passwordmanager.persistence

import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object PasswordEntries : BaseTable<PasswordEntry>("t_password_entries") {
    val id = long("id").primaryKey()
    val username = varchar("username")
    val password = varchar("password")
    val platform = varchar("platform")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = PasswordEntry(
        id = row[id] ?: 0,
        username = row[username].orEmpty(),
        password = row[password].orEmpty(),
        platform = row[platform].orEmpty()
    )
}