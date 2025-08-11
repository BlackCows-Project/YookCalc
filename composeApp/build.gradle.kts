import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += "-Xbinary=bundleId=com.example.yookcalc"
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        // androidMain 소스셋 정의를 한 곳으로 통합합니다.
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
            }
        }

        // iOS 공통 소스셋
        val iosMain = maybeCreate("iosMain").apply {
            dependsOn(commonMain)
        }
        // 각 플랫폼이 iosMain을 사용하도록 연결
        getByName("iosX64Main").dependsOn(iosMain)
        getByName("iosArm64Main").dependsOn(iosMain)
        getByName("iosSimulatorArm64Main").dependsOn(iosMain)


        val wasmJsMain by getting {
            dependencies {
                // 브라우저 API 직접 접근 (기본 포함될 수 있음)
                //implementation(kotlin("stdlib-js"))

                // 웹 소켓 예시 (Ktor 클라이언트 사용 시 commonMain에 core, wasmJsMain에 엔진)
                //implementation("io.ktor:ktor-client-websockets:$ktor_version")

                // JavaScript 라이브러리 통합 예시 (npm)
                //implementation(npm("some-js-library", "1.0.0"))
            }
        }

        // 새 소스셋 추가 (Android + iOS 공통)
        val mobileMain by creating {
            dependsOn(commonMain)
            dependencies { // Voyager 의존성 mobileMain에 추가
                implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:1.0.0")
            }
        }

        // androidMain, iosMain이 mobileMain을 참조하도록
        androidMain.dependsOn(mobileMain)
        iosMain.dependsOn(mobileMain)
    }
}

    android {
        namespace = "com.example.yookcalc"
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        defaultConfig {
            applicationId = "com.example.yookcalc"
            minSdk = libs.versions.android.minSdk.get().toInt()
            targetSdk = libs.versions.android.targetSdk.get().toInt()
            versionCode = 1
            versionName = "1.0"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false  // 프로덕션 빌드 시 true로 변경하고 ProGuard 규칙을 추가하세요.
                // proguardFiles(
                //     getDefaultProguardFile("proguard-android-optimize.txt"),
                //     "proguard-rules.pro"
                // )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }


