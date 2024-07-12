plugins {
    id ("com.google.protobuf" version "0.9.5-SNAPSHOT")
    id("java")
}

group = "com.gitlab.techschool"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation group: 'com.google.protobuf', name: 'protobuf-java', version: '4.27.1'

}

tasks.test {
    useJUnitPlatform()
}