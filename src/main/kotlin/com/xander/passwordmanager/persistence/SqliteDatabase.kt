package com.xander.passwordmanager.persistence

import org.ktorm.database.Database
import org.ktorm.support.sqlite.SQLiteDialect
import org.sqlite.SQLiteDataSource
import java.io.File

object SqliteDatabase {
    const val INIT_SCRIPT = "init-sqlite-data.sql"

    private val dataSource = SQLiteDataSource().apply {
        url = "jdbc:sqlite:${File("").absolutePath}/passwords.db"
    }

    val database = Database.connect(
        dataSource = dataSource,
        dialect = SQLiteDialect()
    )

    init {
        execSqlScript(INIT_SCRIPT, database)
    }

    /*fun connect() : Database {
        return Database.connect (
            url = "jdbc:sqlite::memory:",
            dialect = SQLiteDialect()
        )
    }*/

    fun execSqlScript(filename: String, database: Database) {
        database.useConnection { conn ->
            conn.createStatement().use { statement ->
                javaClass.classLoader
                    ?.getResourceAsStream(filename)
                    ?.bufferedReader()
                    ?.use { reader ->
                        for( sql in reader.readText().split(";") ) {
                            if(sql.any { it.isLetterOrDigit() }) {
                                statement.executeUpdate(sql)
                            }
                        }
                    }
            }
        }
    }
}