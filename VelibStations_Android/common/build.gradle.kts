import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id ("kotlinx-serialization") apply true
}

kotlin {
    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }

    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation ("org.jetbrains.kotlin:kotlin-stdlib-common:1.3.10")
        implementation ("io.ktor:ktor-client:1.0.0")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.0.1")
        implementation ("io.ktor:ktor-client-json:1.0.0")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.9.0")
    }

    sourceSets["androidMain"].dependencies {
        implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.3.10")
        implementation ("io.ktor:ktor-client-json-jvm:1.0.0")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.0.1")
    }

    sourceSets["iosMain"].dependencies {
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)