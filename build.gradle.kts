val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
}


group = "com.example"
version = "0.0.2"
application {
    mainClass.set("com.example.ApplicationKt")

}

tasks.create("stage") {
    dependsOn("installDist")
}
repositories {
    mavenCentral()

}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-jackson:1.6.7")
    implementation("io.ktor:ktor-gson:1.6.7")
    implementation("io.ktor:ktor-server-sessions:1.6.7")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("com.ryanharter.ktor:ktor-moshi:1.0.1")

    implementation ("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-gson:$ktor_version")

    implementation("io.ktor:ktor-freemarker:$ktor_version")

    implementation("io.ktor:ktor-client-auth:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")

    implementation("io.ktor:ktor-locations:$ktor_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    implementation ("com.h2database:h2:$h2_version")
    implementation ("com.zaxxer:HikariCP:3.3.1")
    implementation ("org.postgresql:postgresql:42.3.1")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
}
