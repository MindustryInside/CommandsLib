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
val usefulHash = "6609aa9b6c"

dependencies {
    implementation("com.github.Anuken.Arc:arc-core:$mindustryVerion")
    implementation("com.github.Anuken.Mindustry:core:$mindustryVerion")
    implementation("com.github.xzxadixzx.useful-stuffs:bundle:$usefulHash")
    implementation("com.github.xzxadixzx.useful-stuffs:menu:$usefulHash")
}

tasks.withType<JavaCompile> {
    options.release.set(17)
}
