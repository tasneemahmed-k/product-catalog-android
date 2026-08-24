# Product Catalog Android App

A modern Android product catalog application built with Kotlin and Jetpack Compose.

The application follows the MVVM architecture and separates presentation, data and
dependency-injection responsibilities. It provides a product listing screen, product search, product
details

## Features

- Product listing
- Product search
- Product details screen
- Product rating and review information
- Add to favourites UI
- Add to cart UI
- Share product UI
- Pull-to-refresh
- Loading, empty, and error states
- Unit tests for ViewModels

---

# Project Setup

## Requirements

- Android Studio
- JDK 17
- Android SDK 34
- Android device or emulator
- Gradle

## Getting Started

1. Clone the repository

2. Open the project in Android Studio.

3. Allow Android Studio to synchronize the Gradle files.

4. Make sure the project is configured to use JDK 17.

5. Connect an Android device or start an Android emulator.

6. Run the application using Android Studio.

## Android Configuration

The project currently uses:

- `compileSdk = 34`
- `targetSdk = 34`
- `minSdk = 24`

---

# Architecture Overview

The application follows the **MVVM (Model-View-ViewModel)** architecture.

The main flow of the application is:

```text
UI
 │
 ▼
ViewModel
 │
 ▼
Repository
 │
 ▼
API Service
 │
 ▼
Remote Data Source
```

Data then flows back in the opposite direction:

```text
API
 │
 ▼
Repository
 │
 ▼
ViewModel
 │
 ▼
UI State
 │
 ▼
Compose UI
```

## Presentation Layer

The presentation layer contains:

- Compose screens
- Reusable UI components
- ViewModels
- UI state classes

The ViewModel is responsible for preparing data for the UI and exposing observable state.

The UI observes the ViewModel state using `StateFlow` and lifecycle-aware collection.

---

# Data Layer

The data layer is responsible for obtaining and transforming product data.

## Models

The application uses a `Product` model containing:

- ID
- title
- price
- stock
- description
- category
- images
- rating
- reviews

Reviews contain:

- rating
- reviewer name

The application also uses DTO models for representing the API response.

DTOs are converted into domain models before being passed to the presentation layer.

For example:

```text
ProductDto
    ↓
toProduct()
    ↓
Product
```

---

# Networking Approach

The application uses **Retrofit** for HTTP communication and **Gson** for JSON deserialization.

Retrofit is configured with:

```text
Retrofit
   +
Gson Converter
   +
OkHttp
```

---

# Repository

The repository acts as the boundary between the ViewModel and the data source.

The ViewModel does not communicate directly with Retrofit.

Instead:

```text
ProductsViewModel
       ↓
ProductRepository
       ↓
ProductApiService
```

The repository returns a `DataResult`:

```kotlin
sealed class DataResult<out T> {
    data class Success<T>(
        val data: T
    ) : DataResult<T>()

    data class Error(
        val exception: Throwable
    ) : DataResult<Nothing>()
}
```

---

# Dependency Injection

The project uses **Koin** for dependency injection.

Dependencies such as:

- Retrofit
- ProductApiService
- Repository

are provided through Koin modules.

The general dependency flow is:

```text
Koin
 │
 ├── Retrofit
 │
 ├── OkHttpClient
 │
 ├── ProductApiService
 │
 └── ProductRepository
```

This avoids manually creating dependencies throughout the application and makes the code easier to
test.

It also allows the ViewModel to receive its repository dependency instead of constructing the
repository itself.

---

# Error Handling

The application distinguishes between several possible states.

## Loading

Displayed while products are being retrieved for the first time.

```text
Loading
```

## Success

Displayed when products are successfully loaded.

```text
Success(products)
```

## Empty Response

Displayed when the API/repository returns an empty product list.

```text
No products available.
```

## Error

Errors are converted into user-readable messages before being exposed through the UI state.

The intended error scenarios include:

- No internet connection
- Server errors
- JSON deserialization failures
- Empty API responses
- Product not found
- Invalid product details

The repository reports the underlying exception through `DataResult.Error`, while the ViewModel
converts the error into a message suitable for the UI.

---

# UI

The UI is built using **Jetpack Compose**.

The main screens are:

```text
ProductsScreen
ProductDetailsScreen
```

Reusable components include:

```text
ProductItem
ProductSearchBar
```

The product list uses `LazyColumn`.

The product details screen displays:

- Product image
- Category
- Rating
- Number of reviews
- Product title
- Price
- Description
- Add to cart button
- Share button
- Favourite button

The UI also supports pull-to-refresh on the products screen.

---

# Testing Approach

The project includes unit tests for the ViewModels.

The main areas being tested are:

## Products ViewModel

Tests cover:

- Loading state
- Successful product loading
- Empty product response
- Error state
- State transitions

## Product Details ViewModel

Tests cover:

- Loading state
- Successful product retrieval
- Product not found
- Error state
- State transitions

The ViewModels are tested independently from the Android UI.

Testing tools:

- JUnit
- MockK
- Turbine
- Coroutine test utilities

The general testing flow is:

```text
Arrange
   ↓
Mock repository response
   ↓
Execute ViewModel action
   ↓
Observe StateFlow
   ↓
Assert expected UI state
```

---

# Assumptions Made During Development

The following assumptions were made during development:

### 1. Product images

Products are expected to contain at least one image.

The UI currently accesses the first image:

```kotlin
product.images[0]
```

Therefore, product data should contain a valid image entry.

---

# Technologies

The project uses:

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- StateFlow
- Kotlin Coroutines
- Retrofit
- Gson
- OkHttp
- Koin
- Coil
- JUnit
- MockK
- Turbine

---