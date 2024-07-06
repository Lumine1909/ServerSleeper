plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

group = "io.github.lumine1909"
version = "1.14.514"

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
}