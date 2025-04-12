# WatchOut

WatchOut is a modern Android application for discovering and tracking movies and TV shows. Built with Jetpack Compose and following modern Android development practices.

## Features

- **Multi-Platform Support**: Available for both mobile devices and Android TV
- **Movie Discovery**: Browse and search for movies
- **TV Show Tracking**: Explore and keep track of your favorite TV shows
- **People Profiles**: View information about actors, directors, and other film industry professionals
- **Discover Section**: Explore new and trending content in the world of entertainment

## Architecture

The application is built with a modular architecture:

- **app**: Main application module
- **ui-movie**: Movie-related UI components and screens
- **ui-tvshow**: TV show-related UI components and screens
- **ui-people**: People/cast information UI components
- **ui-discover**: Discovery and exploration UI components
- **common-ui-compose**: Shared UI components and themes
- **data**: Data layer handling API communication and local storage

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Android TV Support**: Compose TV components
- **Architecture**: MVVM with modular design
- **Kotlin Version**: JDK 17
- **Target Android**: API 34 (Android 14)
- **Minimum Android**: API 23 (Android 6.0)

## Building the Project

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Gradle 8.4+

### Building

1. Clone the repository
2. Open the project in Android Studio
3. Select either the "television" or "mobile" build flavor
4. Build and run the application

## Development

The project is structured with the following product flavors:
- **television**: Optimized for Android TV
- **mobile**: Optimized for mobile devices
