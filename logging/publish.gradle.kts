import org.gradle.api.publish.PublishingExtension

apply(plugin = "maven-publish")
apply(plugin = "signing")

// defined in user's global gradle.properties
val sonatype_username: String? by project
val sonatype_password: String? by project

// defined in project's gradle.properties
val groupId: String by project
val licenseName: String by project
val licenseUrl: String by project
// optional properties
val orgId: String? by project
val orgName: String? by project
val orgUrl: String? by project
val developerName: String? by project
val developerId: String? by project

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

            if (name == "jvm") {
                artifact(tasks["javadocJar"])
            }

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
                    if (!developerId.isNullOrEmpty()) {
                        developer {
                            id.set(developerId)
                            name.set(developerName)
                        }
                    }
                    if (!orgId.isNullOrEmpty()) {
                        developer {
                            id.set(orgId)
                            name.set(orgName)
                            organization.set(orgName)
                            organizationUrl.set(orgUrl)
                        }
                    }
                }
                if (!orgName.isNullOrEmpty()) {
                    organization {
                        name.set(orgName)
                        if (!orgUrl.isNullOrEmpty())
                            url.set(orgUrl)
                    }
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
