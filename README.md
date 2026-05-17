嘿,我们也有中文版本,要去看看吗？
Hey, we also have a Chinese version, want to check it out?
[去看看 | go to see](README.zh.md)
---
# CatWindow

Android floating window application providing convenient features like floating ball, time/battery display, quick function buttons, and app list.

## Features

- **Floating Ball**: Draggable floating ball component with gesture support
- **Time Widget**: Displays current time and battery info in bottom-right corner
- **Function Buttons**: Quick access function buttons on the right side
- **App List**: Drawer-style application list panel
- **Gesture Recognition**: Supports double-tap, triple-tap and other gestures
- **Configuration Persistence**: Local JSON storage for user settings
- **Transparent Guide**: Permission guide interface without history records

## Project Structure

```
CatWindow/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/floatwindow/
│   │   │   ├── MainActivity.java          # Permission Guide (Transparent/No History)
│   │   │   ├── SettingsActivity.java      # Settings Interface
│   │   │   ├── FloatWindowService.java    # Main Floating Window Service
│   │   │   ├── ConfigManager.java         # JSON Local Persistence
│   │   │   ├── TapHelper.java             # Double/Triple Tap Gesture Parser
│   │   │   ├── TimeWidget.java            # Bottom-right Time/Battery
│   │   │   ├── FunctionWidget.java        # Right-side Function Buttons
│   │   │   ├── FloatingBall.java          # Floating Ball Component
│   │   │   └── AppListPanel.java          # App List Drawer
│   │   ├── res/                           # Resource Files
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Technical Specifications

| Item | Value |
|------|-------|
| Minimum SDK | Android 8.0 (API 26) |
| Target SDK | Android 15 (API 35) |
| Compile SDK | Android 15 (API 35) |
| Language | Java |
| Build Tool | Gradle Kotlin DSL |
| Version | 1.0 (versionCode: 1) |

## Required Permissions

- `SYSTEM_ALERT_WINDOW` - Floating window permission
- `FOREGROUND_SERVICE` - Foreground service permission
- `FOREGROUND_SERVICE_SPECIAL_USE` - Special use foreground service

## Build & Run

### Requirements

- Android Studio Arctic Fox or higher
- JDK 8 or higher
- Gradle 8.x

### Build Steps

1. Clone repository
```bash
git clone <repository-url>
cd CatWindow
```

2. Open project with Android Studio

3. Sync Gradle and build
```bash
./gradlew assembleDebug
```

4. Install to device
```bash
./gradlew installDebug
```

Or directly click the Run button in Android Studio.

## Usage Instructions

1. On first launch, the app will request floating window permission
2. After authorization, the floating window service will start automatically
3. Access various functions through floating ball or gesture operations
4. Customize floating window behavior in settings

## Core Components

| Component | Description |
|-----------|-------------|
| `FloatWindowService` | Main floating window service, manages all floating window components |
| `FloatingBall` | Draggable floating ball with gesture trigger support |
| `TimeWidget` | Widget displaying time and battery level |
| `FunctionWidget` | Quick function button group |
| `AppListPanel` | Application list drawer panel |
| `ConfigManager` | JSON format configuration management |
| `TapHelper` | Gesture recognition helper class |

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## Contributing

Issues and Pull Requests are welcome.

---

**Note**: This project is for learning and personal use only. Please comply with relevant laws, regulations, and platform policies.
