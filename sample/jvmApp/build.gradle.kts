plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":sample:shared"))
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

sourceSets.main {
    java.srcDirs("src/jvmMain/kotlin")
    resources.srcDirs("src/jvmMain/resources")
}

kotlin {

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


/*
plugins {
    kotlin("multiplatform")
//    kotlin("jvm")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":sample:shared"))
                implementation("org.slf4j:slf4j-api:1.7.30")
                implementation("ch.qos.logback:logback-core:1.2.3")
                implementation("ch.qos.logback:logback-classic:1.2.3")
            }
        }
    }
}
*/


