package net.suqatri.ssh.notify

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ipinfo.api.IPinfo
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Paths
import java.text.SimpleDateFormat
import kotlin.system.exitProcess

val gson: Gson = GsonBuilder().setPrettyPrinting().create()
val classPath = System.getProperty("java.class.path")
val jarPath = Paths.get(classPath).parent.toAbsolutePath().toString()
val configFile = File(jarPath, "config.json")

fun main(args: Array<String>) {

    if (!configFile.exists()) {
        println("Config file does not exist!")

        configFile.createNewFile()
        val defaultJson = gson.toJson(Config())
        configFile.writeText(defaultJson, Charsets.UTF_8)

        println("Default config file has been created!")
        println("Please edit the config file (${configFile.absolutePath})")
        exitProcess(0)
    }

    val config = gson.fromJson(configFile.readText(Charsets.UTF_8), Config::class.java)
    if (config.apiToken == "TOKEN") {
        println("Api token is not set")
        println("Please edit the config file (${configFile.absolutePath})")
        exitProcess(0)
    }

    if (args.size != 2) {
        println("Size of arguments must be 2 (user, ip)")
        exitProcess(0)
    }

    val user = args[0]
    val ipAddress = args[1].replace("(", "").replace(")", "")

    if (config.ipWhitelist.contains(ipAddress) || config.userWhitelist.contains(user)) exitProcess(0)

    val ipInfo = IPinfo.Builder().setToken(config.apiToken).build()
    val response = ipInfo.lookupIP(ipAddress)

    if (config.cityWhitelist.contains(response.city) || config.regionWhitelist.contains(response.region)) exitProcess(0)

    val webhook =
        (config.mobilePhoneNotifyWebHook +
                "?identifier=${config.identifier}" +
                "&user=$user" +
                "&ipAddress=$ipAddress" +
                "&city=${response.city}" +
                "&region=${response.region}" +
                "&country=${response.countryCode}" +
                "&hostname=${response.hostname}" +
                "&organisation=${response.org}" +
                "&postal=${response.postal}" +
                "&timezone=${response.timezone}" +
                "&time=${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())}"
        ).replace(" ", "%20")


    if (webhook.contains("00000000-0000-0000-0000-000000000000")) {
        println("Webhook is not set! Use Macrodroid to create a webhook!")
        println("Please edit the config file (${configFile.absolutePath})")
        exitProcess(0)
    }

    val url = URL(webhook)
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.responseMessage
    connection.disconnect()
    exitProcess(0)
}