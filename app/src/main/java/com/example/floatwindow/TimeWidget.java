package com.example.floatwindow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeWidget extends LinearLayout {
    private final TextView tvTime;
    private final TextView tvBattery;

    public TimeWidget(Context ctx) {
        super(ctx);

        tvTime = new TextView(ctx);
        tvTime.setTextColor(Color.WHITE);
        tvTime.setTextSize(15);
        tvTime.setPadding(0, 0, 12, 0);

        tvBattery = new TextView(ctx);
        tvBattery.setTextColor(Color.WHITE);
        tvBattery.setTextSize(13);

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        setPadding(20, 10, 20, 10);

        addView(tvTime);
        addView(tvBattery);

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable tick = new Runnable() {
            @Override
            public void run() {
                String timeStr = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                tvTime.setText(timeStr);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(tick);

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
                int level = i.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = i.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                if (scale > 0) {
                    int percent = level * 100 / scale;
                    tvBattery.setText(String.format(Locale.getDefault(), "%d%%", percent));
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ctx.registerReceiver(batteryReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            ctx.registerReceiver(batteryReceiver, filter);
        }
    }
}