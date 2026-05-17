package com.example.floatwindow;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(48, 48, 48, 48);

        TextView title = new TextView(this);
        title.setText("悬浮窗设置");
        title.setTextSize(20);
        title.setPadding(0, 0, 0, 24);

        TextView tip = new TextView(this);
        tip.setText("配置已自动持久化。支持拖拽记忆与多窗口状态恢复。");
        tip.setTextSize(14);

        root.addView(title);
        root.addView(tip);
        setContentView(root);
    }
}