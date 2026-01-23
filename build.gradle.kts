plugins {
    id("java-library")
    alias(libs.plugins.maven.publish)
}

group = "io.github.udlepsprog2"
version = "2026.1-RC7"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.launcher)
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    val stdOptions = options as StandardJavadocDocletOptions
    stdOptions.encoding = "UTF-8"
    stdOptions.charSet = "UTF-8"
    stdOptions.links("https://docs.oracle.com/en/java/javase/21/docs/api/")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates("io.github.udlepsprog2", "prog2-lib", "2026.1-RC7")

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
                url.set("https://github.com/jmgimeno")
            }
        }

        scm {
            url.set("https://github.com/udlepsprog2/prog2-lib")
            connection.set("scm:git:https://github.com/udlepsprog2/prog2-lib.git")
            developerConnection.set("scm:git:ssh://git@github.com:udlepsprog2/prog2-lib.git")
        }
    }
}