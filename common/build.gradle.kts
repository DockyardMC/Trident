plugins {
    kotlin("jvm")
}

group = "io.github.dockyardmc.trident.common"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("it.unimi.dsi:fastutil:8.5.13")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}