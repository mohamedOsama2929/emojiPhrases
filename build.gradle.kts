val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}


group = "com.example"
version = "0.0.2"
application {
    mainClass.set("com.example.ApplicationKt")

}

tasks.create("stage") {
    dependsOn("installDist")
}
tasks{
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.example.ApplicationKt"))
        }
    }
}
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        name = "ktor-eap"
    }

}

dependencies {
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-freemarker:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-auth:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-locations:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-status-pages:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-default-headers:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-core:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-netty:2.0.0-eap-256")
    implementation("io.ktor:ktor-server-sessions:2.0.0-eap-256")
    implementation("io.ktor:ktor-client-serialization:2.0.0-eap-256")
    implementation("io.ktor:ktor-client-gson:2.0.0-eap-256")
    implementation("io.ktor:ktor-client-auth:2.0.0-eap-256")
    implementation("io.ktor:ktor-auth-jwt:2.0.0-eap-256")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("com.ryanharter.ktor:ktor-moshi:1.0.1")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    implementation ("com.h2database:h2:$h2_version")
    implementation ("com.zaxxer:HikariCP:3.3.1")
    implementation ("org.postgresql:postgresql:42.3.1")
    testImplementation("io.ktor:ktor-server-tests:2.0.0-eap-256")

    implementation("io.ktor:ktor-websockets:$ktor_version")
}
