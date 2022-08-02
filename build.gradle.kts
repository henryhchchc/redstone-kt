@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.7.10"
}

group = "net.henryhc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {

    targets.all {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
            }
        }
    }

    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xbackend-threads=0",
                    "-Xextended-compiler-checks"
                )
            }
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    val hostOs = System.getProperty("os.name")
    val arch = System.getProperty("os.arch")
    val nativeTargetName = "native"
    val nativeTarget = when (hostOs) {
        "Mac OS X" -> when (arch) {
            "aarch64" -> macosArm64(nativeTargetName)
            "x86_64" -> macosX64(nativeTargetName)
            else -> throw GradleException("Architecture $arch is not supported on macOS")
        }

        "Linux" -> when (arch) {
            "aarch64" -> linuxArm64(nativeTargetName)
            "amd64" -> linuxX64(nativeTargetName)
            else -> throw GradleException("Architecture $arch is not supported on Linux")
        }

        else -> throw GradleException("Host OS is not supported.")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.arrow-kt:arrow-core:1.1.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}
