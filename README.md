<h1 align="center"> Comic App </h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://kotlinlang.org"><img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.4.xxx-blue"/></a>
  <img alt="MVVM" src="https://img.shields.io/badge/MVVM-Architecture-orange"/>
  <a href="https://developer.android.com/kotlin/coroutines"><img alt="Coroutines" src="https://img.shields.io/badge/Coroutines-Asynchronous-red"/></a>
</p>

<p align="center">
Comic app is a small Multi-Module demo application to demonstrate modern Android application tech-stacks with a Multi-module and MVVM architecture, with emphasize on Unit & UI testing.
</p>

## Tech stack & Open-source libraries

- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/index.html)
- [Koin](https://insert-koin.io) for dependency injection.
- [JetPack](https://developer.android.com/jetpack)
    - Fragment-ktx - A set of Kotlin extensions that helps with fragment lifecycle.
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Allows you to more
      easily write code that interacts with views
    - Fragment navigation graph
    - ViewModel - UI related data holder, lifecycle aware.
    - Room Persistence - construct a database using the abstract layer.
- Architecture
    - Multi-module design for the app.
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Repository pattern (NetworkBoundResource)
    - Clean Architecture approach.
- [Gradle KotlinDsl](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [GSON](https://github.com/google/gson) - A Modern JSON library for Android.
- [Picasso](https://github.com/bumptech/glide) - For Loading images from Urls.
- [Barista](https://github.com/AdevintaSpain/Barista) - Barista makes developing UI test faster,
  easier and more predictable.
- [Ktlint](https://github.com/pinterest/ktlint)- An anti-bikeshedding Kotlin linter with a built-in
  formatter.
- [Material-Components](https://github.com/material-components/material-components-android) -
  Material design components.

## Architecture

Comic app is Multi-moduled application with a meaningful separation for layers and features with the
necessary grouping. With MVVM architecture with an additional Domain layer for each module by
itself.

## Tasks

- [x] Creating the project structure

- [x] Implementing all features

- [x] Writing test cases

# License

```xml
Designed and developed by 2021 Ahmed shaban

    Licensed under the Apache License, Version 2.0 (the "License");you may not use this file except in compliance with the License.You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, softwaredistributed under the License is distributed on an "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.See the License for the specific language governing permissions andlimitations under the License.
```
