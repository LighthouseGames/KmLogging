plugins {
    kotlin("js")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.3")
    implementation(project(":sample:shared"))
}

kotlin {
    js {
        browser {
            binaries.executable()
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}
