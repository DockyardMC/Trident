plugins {
    kotlin("jvm") version "1.9.23"
}

group = "io.github.dockyardmc.trident"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}