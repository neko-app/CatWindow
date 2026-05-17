# CatWindow

Android 悬浮窗应用，提供便捷的悬浮球、时间电量显示、快速功能按钮和应用列表等功能。

## 功能特性

- **悬浮球**：可拖拽的悬浮球组件，支持手势操作
- **时间小部件**：右下角显示当前时间和电量信息
- **功能按钮组**：右侧快捷功能按钮
- **应用列表**：抽屉式应用列表面板
- **手势识别**：支持双击、三击等手势操作
- **配置持久化**：使用 JSON 本地存储用户配置
- **透明引导页**：无历史记录的权限引导界面

## 项目结构

```
CatWindow/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/floatwindow/
│   │   │   ├── MainActivity.java          # 权限引导（透明/无历史）
│   │   │   ├── SettingsActivity.java      # 设置界面
│   │   │   ├── FloatWindowService.java    # 悬浮窗主控服务
│   │   │   ├── ConfigManager.java         # JSON 本地持久化
│   │   │   ├── TapHelper.java             # 双击/三击手势解析
│   │   │   ├── TimeWidget.java            # 右下角时间/电量
│   │   │   ├── FunctionWidget.java        # 右侧功能按钮组
│   │   │   ├── FloatingBall.java          # 悬浮球组件
│   │   │   └── AppListPanel.java          # 应用列表抽屉
│   │   ├── res/                           # 资源文件
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## 技术规格

| 项目 | 值 |
|------|-----|
| 最低 SDK | Android 8.0 (API 26) |
| 目标 SDK | Android 15 (API 35) |
| 编译 SDK | Android 15 (API 35) |
| 开发语言 | Java |
| 构建工具 | Gradle Kotlin DSL |
| 版本 | 1.0 (versionCode: 1) |

## 所需权限

- `SYSTEM_ALERT_WINDOW` - 悬浮窗权限
- `FOREGROUND_SERVICE` - 前台服务权限
- `FOREGROUND_SERVICE_SPECIAL_USE` - 特殊用途前台服务

## 构建与运行

### 环境要求

- Android Studio Arctic Fox 或更高版本
- JDK 8 或更高版本
- Gradle 8.x

### 构建步骤

1. 克隆仓库
```bash
git clone <repository-url>
cd CatWindow
```

2. 使用 Android Studio 打开项目

3. 同步 Gradle 并构建
```bash
./gradlew assembleDebug
```

4. 安装到设备
```bash
./gradlew installDebug
```

或直接点击 Android Studio 的运行按钮。

## 使用说明

1. 首次启动时，应用会请求悬浮窗权限
2. 授权后，悬浮窗服务将自动启动
3. 通过悬浮球或手势操作访问各项功能
4. 可在设置中自定义悬浮窗行为

## 核心组件说明

| 组件 | 描述 |
|------|------|
| `FloatWindowService` | 悬浮窗主服务，管理所有悬浮窗组件 |
| `FloatingBall` | 可拖拽悬浮球，支持手势触发 |
| `TimeWidget` | 显示时间和电量的小部件 |
| `FunctionWidget` | 快捷功能按钮组 |
| `AppListPanel` | 应用列表抽屉面板 |
| `ConfigManager` | JSON 格式的配置管理 |
| `TapHelper` | 手势识别辅助类 |

## 许可证

本项目采用 GNU General Public License v3.0 许可证 - 详见 [LICENSE](LICENSE) 文件。

## 贡献

欢迎提交 Issue 和 Pull Request。

---

**注意**：本项目仅供学习和个人使用，请遵守相关法律法规和平台政策。
