plugins {
    id(GradlePluginId.ANDROID_LIB)
    id(GradlePluginId.KAPT)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
    id("kotlin-android")
}

dependencies {
    implementation(LibraryDependency.NAVIGATION_FRAGMENT)
    implementation(LibraryDependency.NAVIGATION_UI)
    localRoomDependencies()
    api(project(ModulesDependency.COMMON))
    api(project(ModulesDependency.REMOTE))
    api(project(ModulesDependency.MODEL))
    api(project(ModulesDependency.LOCAL))
    addTestDependencies()
}
