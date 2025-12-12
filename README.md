# <img width="35" height="31" alt="final lab logo" src="https://github.com/user-attachments/assets/01259e4f-48a3-43e5-82af-21bba79bf497" /> E-reader — Online Book Store 📱📖

E-reader is a Kotlin Android e-commerce app for browsing and purchasing books. It offers a clean UI, dark mode support, and common shopping features like favorites and a cart.

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Screenshots](#screenshots)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [License](#license)

---

## Overview
E-Reader provides a seamless experience for discovering, browsing, and purchasing books. Built with Kotlin and Android best practices, the app follows a single-activity architecture with multiple fragments.

---

## Features
- 🔐 Authentication
  - User registration with form validation
  - Secure login system
  - Password visibility toggle
  - Session management
- 📚 Book Browsing
  - Grid / List views of books
  - Category filtering (chips)
  - Book details: cover, title, author, price
  - Add to favorites
  - Add to cart
- 🛒 Shopping Cart
  - View cart items
  - Adjust item quantities
  - Remove items from cart
  - Real-time price calculation
  - Free shipping offers
- ⚙️ Settings
  - Dark / Light mode toggle
  - User profile management
  - Logout

---

## Tech Stack
- Language: Kotlin
- Architecture: Single Activity with Fragments
- Database: SQLite (local storage)
- UI: RecyclerView, Material Design Components
- Navigation: Fragment transactions
- Min SDK: 24
- Target SDK: 34

---

## Project Structure
```
app/
├── src/main/
│   ├── java/com/example/e_readerbookstore/
│   │   ├── database/
│   │   │   └── DatabaseHelper.kt
│   │   ├── model/
│   │   │   ├── Book.kt
│   │   │   ├── CartItem.kt
│   │   │   └── User.kt
│   │   ├── ui/
│   │   │   ├── auth/
│   │   │   │   ├── LoginFragment.kt
│   │   │   │   └── SignUpFragment.kt
│   │   │   ├── cart/
│   │   │   │   ├── CartAdapter.kt
│   │   │   │   └── CartFragment.kt
│   │   │   ├── home/
│   │   │   │   ├── BookAdapter.kt
│   │   │   │   ├── CategoryFilterAdapter.kt
│   │   │   │   └── HomeFragment.kt
│   │   │   └── settings/
│   │   │       └── SettingsFragment.kt
│   │   └── MainActivity.kt
│   └── res/
│       ├── layout/
│       ├── values/
│       └── drawable/
```

---

## Screenshots
(neb2a n7ot screenshots hena ba2a )
- Login Screen
- Home / Book Grid
- Cart and Favorite Screen
- Settings Screen

---

## Getting Started

Clone the repo:
```bash
git clone https://github.com/Mohamed-MX/E-reader-Online-Book-Store-.git
```

Open the project in Android Studio, sync Gradle, and run on an emulator or device.

---

## Prerequisites
- Android Studio Arctic Fox or later
- Kotlin 1.8+
- Android SDK (min 24, target 34)

---

