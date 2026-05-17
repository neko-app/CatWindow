package com.example.floatwindow;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigManager {
    private final SharedPreferences prefs;

    public ConfigManager(Context context) {
        prefs = context.getSharedPreferences("float_config", Context.MODE_PRIVATE);
    }

    public void saveInt(String key, int value) { prefs.edit().putInt(key, value).apply(); }
    public int getInt(String key, int def) { return prefs.getInt(key, def); }
    public void saveBoolean(String key, boolean value) { prefs.edit().putBoolean(key, value).apply(); }
    public boolean getBoolean(String key, boolean def) { return prefs.getBoolean(key, def); }
}