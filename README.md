  ![License: All Rights Reserved](https://img.shields.io/badge/license-All%20Rights%20Reserved-red)

⚠️ **Notice:**  
This project is published publicly **for portfolio and skill demonstration purposes only**.  

- ✅ Employers and recruiters are welcome to view the code.  
- ❌ The code is **not licensed for copying, reuse, modification, or distribution**.  
- 📄 See the [LICENSE](./LICENSE) file for full details.  


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

![Screenshot 2025-06-09 134734](https://github.com/user-attachments/assets/5ce949ea-ae24-485e-8b99-e0be5983c4ca)
![Screenshot 2024-09-29 163412](https://github.com/user-attachments/assets/fa70e56a-e62c-406a-bcbe-0791c1036173)
![Screenshot 2024-09-29 163123](https://github.com/user-attachments/assets/95ff6fd1-b2f9-4425-8b0b-e17649bab6ae)
![Screenshot 2024-09-29 163603](https://github.com/user-attachments/assets/3ed5080f-1381-46ca-941b-d2a6dec16612)
![Screenshot 2024-09-29 163327](https://github.com/user-attachments/assets/53cf2200-d75e-4aa7-b820-82763ee8b1e6)


📬 Contact
Willie Gaines • williegaines5@gmail.com

