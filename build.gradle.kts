plugins {
    id("java")
}

group = "inside"
version = "1.0-BETA"

repositories {
    mavenCentral()
    maven(url = "https://www.jitpack.io")
}

dependencies {
    implementation("com.github.Anuken.Arc:arc-core:v144")
    implementation("com.github.Anuken.Mindustry:core:v144")
}
