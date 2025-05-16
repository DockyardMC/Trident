import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "2.3.1" // Adds runServer and runMojangMappedServer tasks for testing
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0" // Generates plugin.yml based on the Gradle config
}

group = "io.github.dockyardmc.trident.paper"
version = parent!!.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven {
        name = "devOS"
        url = uri("https://mvn.devos.one/releases")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

dependencies {
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")
    implementation(project(":common"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

bukkitPluginYaml {
    main = "io.github.dockyardmc.trident.paper.TestPlugin"
    load = BukkitPluginYaml.PluginLoadOrder.STARTUP
    authors.add("Author")
    apiVersion = "1.21.5"
    name = "test"
}