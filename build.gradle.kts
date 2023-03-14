plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "net.suqatri.ssh.notify"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("io.ipinfo:ipinfo-api:2.1")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    withType<JavaCompile> {
        options.release.set(17)
        options.encoding = "UTF-8"
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("ssh-login-notify.jar")
    }
}

tasks.jar {
    dependsOn(tasks.shadowJar)
    manifest {
        attributes(
            "Main-Class" to "net.suqatri.ssh.notify.SSHNotifyMainKt"
        )
    }
}