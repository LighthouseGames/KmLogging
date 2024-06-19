plugins {
    kotlin("multiplatform")
}

kotlin {
    js {
        browser {
        }
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-html:0.8.0")
                implementation(project(":sample:shared"))
            }
        }
    }
}
