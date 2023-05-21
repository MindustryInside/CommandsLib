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
        register("release", MavenPublication::class) {
            from(componentslistOf("java"))
            artifactId = "commands"
        }
    }
}

val mindustryVerion = "v144.1"

dependencies {
    compileOnly("com.github.Anuken.Arc:arc-core:$mindustryVerion")
    compileOnly("com.github.Anuken.Mindustry:core:$mindustryVerion")
    testCompileOnly("com.github.Anuken.Arc:arc-core:$mindustryVerion")
    testCompileOnly("com.github.Anuken.Mindustry:core:$mindustryVerion")
}

tasks.withType<JavaCompile> {
    options.release.set(17)
}
