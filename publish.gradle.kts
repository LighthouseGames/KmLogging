import org.gradle.api.publish.PublishingExtension

apply(plugin = "maven-publish")

val bintray_user: String by project
val bintray_key: String by project
val bintrayOrg: String by project
val bintrayRepo: String by project

val groupId: String by project
val artifactId: String by extra
val artifactVersion: String by extra
val libraryName: String by extra
val libraryDescription: String by extra
val gitUrl: String by extra
val licenseName: String by project
val licenseUrl: String by project
val developerName: String by project
val developerId: String by project

project.group = groupId
project.version = artifactVersion

configure<PublishingExtension> {
    publications {
        withType<MavenPublication> {
            groupId = groupId
            artifactId = artifactId
            version = artifactVersion

            pom {
                name.set(libraryName)
                description.set(libraryDescription)
                url.set(gitUrl)

                licenses {
                    license {
                        name.set(licenseName)
                        url.set(licenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(developerId)
                        name.set(developerName)
                    }
                }
                organization {
                    name.set(bintrayOrg)
                }
            }
        }
    }

    repositories {
        maven("https://api.bintray.com/maven/${bintrayOrg}/${bintrayRepo}/${artifactId}/;publish=1") {
            credentials {
                username = bintray_user
                password = bintray_key
            }
        }
    }
}
