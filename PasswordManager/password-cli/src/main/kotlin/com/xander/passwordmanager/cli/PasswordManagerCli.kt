package com.xander.passwordmanager.cli

import com.xander.passwordmanager.cli.helper.PrettyPrinter
import com.xander.passwordmanager.core.repository.*
import picocli.CommandLine
import java.util.concurrent.Callable
import kotlin.system.exitProcess

/**
 * Entrypoint of the CLI
 *
 * @author Xander Van der Weken
 */
@CommandLine.Command(
    name = "password-manager",
    version = ["1.0"],
    mixinStandardHelpOptions = true,
    description = [PrettyPrinter.asciiArt, "The Ultimate Password Manager"]
)
class PasswordManagerCli : Callable<Int> {

    private val repository = RepositoryFactory.create()

    @CommandLine.Spec
    lateinit var spec: CommandLine.Model.CommandSpec

    /**
     * Method for handling the add Method in the CLI
     *
     * @param username username to add
     * @param password password to add
     * @param platform platform to add
     */
    @CommandLine.Command(name = "add", description = ["Add new Password"])
    fun createPassword(
        @CommandLine.Option(names = ["-u", "--username"], required = true) username: String,
        @CommandLine.Option(names = ["-p", "--password"], required = true) password: String,
        @CommandLine.Option(names = ["-l", "--platform"], required = true) platform: String
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
    @CommandLine.Command(name = "get", description = ["Get Password"])
    fun getPassword(
        @CommandLine.Option(names = ["-u", "--username"], required = true) username: String,
        @CommandLine.Option(names = ["-l", "--platform"], required = true) platform: String
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