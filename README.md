# Wear OS Password Vault

A secure, on-device password manager for Wear OS built in Kotlin, powered by Firebase Authentication & Firestore, with watch-face complication support.

---

## 🚀 Features

- **Email/password sign-up & sign-in** via Firebase Auth  
- **PIN-protected entry**: optional PIN on launch  
- **Firestore storage** for user profiles & encrypted passwords  
- **CRUD operations**: add, view, search, update & remove password entries  
- **Identity Info** screen to show user details  
- **Settings** to toggle PIN requirement or reset PIN  
- **Wear OS Complication** to surface quick stats on your watch-face  
- **Material-themed UI** with custom drawables & XML layouts  

---

## 📂 Project Structure

/app
├─ manifests/AndroidManifest.xml
├─ java/kotlin/com.example.myapplication
│ ├─ complication/ # Wear OS complication service
│ │ └─ MainComplicationService.kt
│ └─ presentation/ # All screens & activities
│ ├─ Login.kt
│ ├─ SignUp.kt
│ ├─ PinSetup.kt
│ ├─ MainScreen.kt
│ ├─ AddPWD.kt
│ ├─ ViewPWD.kt
│ ├─ SearchPWD.kt
│ ├─ UpdatePWD.kt
│ ├─ RemovePWD.kt
│ ├─ IdentityInfo.kt
│ ├─ Settings.kt
│ └─ Theme.kt
├─ res/
│ ├─ layout/ # XML screen layouts
│ ├─ drawable/ # Custom backgrounds, buttons, icons
│ ├─ values/ # colors, strings, styles
│ └─ font/ # custom font definitions
└─ Gradle Scripts/
├─ build.gradle.kts
└─ settings.gradle.kts

🧩 Usage
First launch:

Sign up with email & password

Set your 4-digit PIN

Subsequent launches:

If PIN required, enter it to unlock

Otherwise you go straight to the Main Screen

Main Screen:

Add, view, search, update or remove stored passwords

View/edit your user info under Identity Info

Toggle PIN-on-launch or reset PIN in Settings

📬 Contact
Willie Gaines • williegaines5@gmail.com

