package com.xander.passwordmanager.cli

import com.xander.passwordmanager.persistence.PasswordEntry
import picocli.CommandLine

class PrettyPrinter(val spec: CommandLine.Model.CommandSpec) {
    companion object {
        const val asciiArt =
"""
    ____                  __  ___          
   / __ \____ ___________/  |/  /___ _____ 
  / /_/ / __ `/ ___/ ___/ /|_/ / __ `/ __ \
 / ____/ /_/ (__  |__  ) /  / / /_/ / / / /
/_/    \__,_/____/____/_/  /_/\__,_/_/ /_/                                         
"""
    }

    fun printAdded(username: String, platform: String) {
        spec.commandLine().out.println("""
            A Password for $username on $platform has been added.
        """.trimIndent())
    }

    fun printGot(username: String, password: String, platform: String) {
        spec.commandLine().out.println("""
            This password was found for $username on $platform: $password
        """.trimIndent())
    }

    fun printNotGot(username: String, platform: String) {
        spec.commandLine().out.println("""
            No Password found for $username on $platform
        """.trimIndent())
    }
}