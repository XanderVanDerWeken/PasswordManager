package com.xander.passwordmanager.cli.helper

import picocli.CommandLine

/**
 * Pretty Printer, to make the Output pretty in the CLI
 *
 * @author Xander Van der Weken
 */
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

    /**
     * Print Method for an added password
     *
     * @param username username for which a password was added
     * @param platform platform for which a password was added
     */
    fun printAdded(username: String, platform: String) {
        spec.commandLine().out.println("""
            A Password for $username on $platform has been added.
        """.trimIndent())
    }

    /**
     * Print Method for a retrieved password
     *
     * @param username username for which was retrieved
     * @param password password which was retrieved
     * @param platform platform for which was retrieved
     */
    fun printGot(username: String, password: String, platform: String) {
        spec.commandLine().out.println("""
            This password was found for $username on $platform: $password
        """.trimIndent())
    }

    /**
     * Print Method for when retrieval failed
     *
     * @param username given username where no password was found
     * @param platform given platform where no password was found
     */
    fun printNotGot(username: String, platform: String) {
        spec.commandLine().out.println("""
            No Password found for $username on $platform
        """.trimIndent())
    }
}