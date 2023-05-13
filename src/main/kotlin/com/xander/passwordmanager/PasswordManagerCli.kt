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

    @Command(name = "add", description = [PrettyPrinter.asciiArt, "Add new Password"])
    fun createPassword(
        @Option(names = ["-u", "--username"], required = true) username: String,
        @Option(names = ["-p", "--password"], required = true) password: String,
        @Option(names = ["-l", "--platform"], required = true) platform: String
    ) {
        spec.commandLine().out.println("Actually in add method")
        repository.addPassword( username, password, platform )
        spec.commandLine().out.println("Great")
    }

    @Command(name = "get", description = [PrettyPrinter.asciiArt, "Get Password"])
    fun getPassword(
        @Option(names = ["-u", "--username"], required = true) username: String,
        @Option(names = ["-l", "--platform"], required = true) platform: String
    ) {
        val password = repository.getPassword( platform, username )
        if(password == null)
            spec.commandLine().out.println("Password not found")
        else
            spec.commandLine().out.println( "Password is $password" )
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