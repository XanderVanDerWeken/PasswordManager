package com.xander.passwordmanager

import com.xander.passwordmanager.cli.PrettyPrinter
import com.xander.passwordmanager.persistence.SqliteDatabase
import com.xander.passwordmanager.repository.IPasswordRepository
import com.xander.passwordmanager.repository.PasswordRepositoryImpl
import org.ktorm.database.Database
import picocli.CommandLine
import picocli.CommandLine.*
import picocli.CommandLine.Model.CommandSpec
import java.util.concurrent.Callable
import kotlin.system.exitProcess

/**
 * Entrypoint of the CLI
 *
 * @author Xander Van der Weken
 */
@Command(
    name = "password-manager",
    version = ["1.0"],
    mixinStandardHelpOptions = true,
    description = [PrettyPrinter.asciiArt, "The Ultimate Password Manager"]
)
class PasswordManagerCli : Callable<Int> {

    private val database: Database
    private val repository : IPasswordRepository

    init {
        println("Execute Init")
        this.database = SqliteDatabase.database
        this.repository = PasswordRepositoryImpl( database )
    }

    @Spec
    lateinit var spec: CommandSpec

    /**
     * Method for handling the add Method in the CLI
     *
     * @param username username to add
     * @param password password to add
     * @param platform platform to add
     */
    @Command(name = "add", description = ["Add new Password"])
    fun createPassword(
        @Option(names = ["-u", "--username"], required = true) username: String,
        @Option(names = ["-p", "--password"], required = true) password: String,
        @Option(names = ["-l", "--platform"], required = true) platform: String
    ) {
        repository.addPassword( username, password, platform )
        PrettyPrinter(spec).printAdded(username, platform)
    }

    /**
     * Method for handling the get Method in the CLI
     *
     * @param username username to get
     * @param platform platform to get
     */
    @Command(name = "get", description = ["Get Password"])
    fun getPassword(
        @Option(names = ["-u", "--username"], required = true) username: String,
        @Option(names = ["-l", "--platform"], required = true) platform: String
    ) {
        val password = repository.getPassword( platform, username )
        if(password == null)
            PrettyPrinter(spec).printNotGot(username, platform)
        else
            PrettyPrinter(spec).printGot(username, password, platform)
    }

    override fun call(): Int {
        spec.commandLine().usage(System.out)
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            exitProcess(CommandLine(PasswordManagerCli()).execute(*args))
        }
    }
}