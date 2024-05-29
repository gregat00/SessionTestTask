I started the test task at 14:00 CET 28.05.2024. During the implementation, I focused on the rules of building projects from scratch, using Clean Architecture.

**Stack:**
- Compose
- Room
- Coroutines
- Flow
- Clean Architecture
- Koin

**Environment:**
- Android Studio Version: Android Studio Giraffe | 2022.3.1 Patch 1  
- Kotlin Version: 1.9.10  
- Gradle Version: 8.0
- Compose Version: 1.9.0
- Java Version: 17

**App Description:**

The text size on the screen changes based on the rotation angle of the screen using gyroscope data. When the app goes into the background, the timestamp is saved. Upon returning to the foreground, the timestamp is compared with the current time, and the session counter is incremented by 1 if more than 10 minutes have passed.

**Demo:**

https://github.com/gregat00/SessionTestTask/assets/169467265/ef11ea8e-66c4-4b75-a23c-4f8c2e1b7c61

**Note:**
For demo purposes I decreased session count timer to 5 seconds in background.

