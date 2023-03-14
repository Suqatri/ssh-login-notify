package net.suqatri.ssh.notify

data class Config(
    val identifier: String = "node01",
    val apiToken: String = "TOKEN",
    val ipWhitelist: List<String> = listOf("127.0.0.1"),
    val cityWhitelist: List<String> = listOf(),
    val regionWhitelist: List<String> = listOf(),
    val userWhitelist: List<String> = listOf(),
    val mobilePhoneNotifyWebHook: String = "https://trigger.macrodroid.com/00000000-0000-0000-0000-000000000000/ssh-login"
)