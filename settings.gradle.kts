pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // TBD Releases
        maven("https://blockxyz.jfrog.io/artifactory/tbd-oss-releases-maven2/")
        // Thirdparty dependencies of TBD projects not in Maven Central
        maven("https://blockxyz.jfrog.io/artifactory/tbd-oss-thirdparty-maven2/")
    }
}

rootProject.name = "Tbdexy"
include(":app")
 