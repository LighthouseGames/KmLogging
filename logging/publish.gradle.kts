import org.gradle.api.publish.PublishingExtension

apply(plugin = "maven-publish")
apply(plugin = "signing")

val sonatype_username: String by project
val sonatype_password: String by project

val orgName: String by project
val groupId: String by project
val licenseName: String by project
val licenseUrl: String by project
val developerName: String by project
val developerId: String by project

val artifactId: String by extra
val artifactVersion: String by extra
val libraryName: String by extra
val libraryDescription: String by extra
val gitUrl: String by extra

project.group = groupId
project.version = artifactVersion

configure<PublishingExtension> {
    val publishing = this
    publications {
        withType<MavenPublication> {
            groupId = groupId
            artifactId = artifactId
            version = artifactVersion

            artifact(tasks["javadocJar"])

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
                scm {
                    url.set(gitUrl)
                }
                developers {
                    developer {
                        id.set(developerId)
                        name.set(developerName)
                    }
                }
                organization {
                    name.set(orgName)
                }
            }
        }
    }

    repositories {
        maven("https://oss.sonatype.org/service/local/staging/deploy/maven2") {
            credentials {
                username = sonatype_username
                password = sonatype_password
            }
        }
    }

    configure<SigningExtension> {
        sign(publishing.publications)
    }
}
