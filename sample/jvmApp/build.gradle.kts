plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":sample:shared"))
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-core:1.2.9")
    implementation("ch.qos.logback:logback-classic:1.2.9")
}

sourceSets.main {
    java.srcDirs("src/jvmMain/kotlin")
    resources.srcDirs("src/jvmMain/resources")
}
