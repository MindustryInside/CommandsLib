plugins {
    java
}

version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://www.jitpack.io")
}

val mindustryVerion = "v144"

dependencies {
    implementation("com.github.Anuken.Arc:arc-core:$mindustryVerion")
    implementation("com.github.Anuken.Mindustry:core:$mindustryVerion")
}

tasks.withType<JavaCompile> {
    options.release.set(17)
}
