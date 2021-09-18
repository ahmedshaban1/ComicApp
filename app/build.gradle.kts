plugins {
    id(GradlePluginId.ANDROID_APP)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
}

dependencies {
    commonDevelopmentDependencies()
    api(project(ModulesDependency.COMMON))


}