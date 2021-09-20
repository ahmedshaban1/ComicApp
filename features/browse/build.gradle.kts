plugins {
    id(GradlePluginId.ANDROID_LIB)
    id(GradlePluginId.KAPT)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
    id("kotlin-android")
}

dependencies {
    implementation(LibraryDependency.NAVIGATION_FRAGMENT)
    implementation(LibraryDependency.NAVIGATION_UI)
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    localRoomDependencies()
    api(project(ModulesDependency.COMMON))
    api(project(ModulesDependency.REMOTE))
    api(project(ModulesDependency.MODEL))
    api(project(ModulesDependency.LOCAL))

    // implementation(project(ModulesDependency.UI))

    // addTestDependencies()
}
