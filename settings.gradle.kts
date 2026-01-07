rootProject.name = "prog2-lib"

plugins {
    id("com.gradleup.nmcp.settings").version("1.4.3")
}

nmcpSettings {
    centralPortal {
        username.set(providers.gradleProperty("mavenCentralUsername").orNull
            ?: System.getenv("MAVEN_CENTRAL_USERNAME"))
        password.set(providers.gradleProperty("mavenCentralPassword").orNull
            ?: System.getenv("MAVEN_CENTRAL_PASSWORD"))
    }
}