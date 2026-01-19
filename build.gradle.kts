
plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "io.github.udlepsprog2"
version = "2026.1-RC2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    // Kotlin DSL; cast to StandardJavadocDocletOptions for more options
    (options as StandardJavadocDocletOptions).apply {
        encoding = "UTF-8"
        charSet = "UTF-8"
        // Link to JDK 21 API docs
        links("https://docs.oracle.com/en/java/javase/21/docs/api/")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("prog2-lib")
                description.set("A small Java library used by the Escola Politècnica Superior (EPS) of the Universitat de Lleida (UdL) for the course \"Programació 2\"")
                url.set("https://github.com/udlepsprog2/prog2-lib")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("jmgimeno")
                        name.set("Juan Manuel Gimeno Illa")
                        email.set("jmgimeno@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/udlepsprog2/prog2-lib.git")
                    developerConnection.set("scm:git:ssh://git@github.com:udlepsprog2/prog2-lib.git")
                    url.set("https://github.com/udlepsprog2/prog2-lib")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

tasks.withType<Sign>().configureEach {
    onlyIf { project.hasProperty("signing.keyId") || System.getenv("GPG_KEY_ID") != null }
}