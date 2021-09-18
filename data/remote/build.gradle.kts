plugins {
    id(GradlePluginId.ANDROID_LIB)
    id(GradlePluginId.KAPT)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)

}

dependencies {
    implementation(project(ModulesDependency.COMMON))
    lifeCycleDependencies()
    diDependencies()
    networkDependencies()
}
