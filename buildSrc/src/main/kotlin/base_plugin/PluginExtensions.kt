package base_plugin

import AndroidConfig
import BuildType
import GradlePluginId
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureDefaultPlugins() {
    plugins.apply(GradlePluginId.ANDROID)
}

private typealias AndroidBaseExtension = BaseExtension

internal fun Project.configureAndroidApp() = this.extensions.getByType<AndroidBaseExtension>().run {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        buildTypes {
            getByName(BuildType.DEBUG) {
                isTestCoverageEnabled = true
            }
            getByName(BuildType.RELEASE) {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    project.tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    viewBinding.isEnabled = true


}


