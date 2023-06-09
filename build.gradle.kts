plugins {
    `java-library`
    `maven-publish`
}

version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://www.jitpack.io")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])

            groupId = "inside.commands"
            artifactId = "commands-lib"
            //version = project.version

            pom {
                name.set("CommandsLib")
                description.set("Library for Mindustry plugins and mods for convenient, safe and easy command registering.")
                url.set("https://github.com/MindustryInside/CommandsLib")
            }
        }
    }

    repositories {
        maven("https://jitpack.io")
    }
}

val mindustryVerion = "v144.1"

dependencies {
    compileOnly("com.github.Anuken.Arc:arc-core:$mindustryVerion")
    compileOnly("com.github.Anuken.Mindustry:core:$mindustryVerion")
    compileOnly("com.github.Anuken.Mindustry:server:$mindustryVerion")

    testCompileOnly("com.github.Anuken.Arc:arc-core:$mindustryVerion")
    testCompileOnly("com.github.Anuken.Mindustry:core:$mindustryVerion")
    testCompileOnly("com.github.Anuken.Mindustry:server:$mindustryVerion")
}

tasks.withType<JavaCompile> {
    options.release.set(17)
}

tasks.withType<Jar> {
    manifest {
        attributes("Automatic-Module-Name" to "inside.commandslib")
        attributes("Implementation-Version" to version)
    }
}
