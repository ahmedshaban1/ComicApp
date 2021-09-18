plugins {
    id(GradlePluginId.ANDROID_LIB)
    id(GradlePluginId.KAPT)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)

}

dependencies {
    implementation(LibraryDependency.NAVIGATION_FRAGMENT)
    implementation(LibraryDependency.NAVIGATION_UI)
    localRoomDependencies()
    api(project(ModulesDependency.COMMON))
    api(project(ModulesDependency.REMOTE))
    api(project(ModulesDependency.MODEL))
    api(project(ModulesDependency.LOCAL))

    //implementation(project(ModulesDependency.UI))

    //addTestDependencies()


}

