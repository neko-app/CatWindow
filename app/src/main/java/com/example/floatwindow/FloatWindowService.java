package com.example.floatwindow;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class FloatWindowService extends Service {
    private WindowManager wm;
    private ConfigManager config;
    private View timeView, funcView, ballView, appListView;
    private WindowManager.LayoutParams ballLp;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {
            String action = i.getAction();
            if (action == null) return;
            switch (action) {
                case "ACTION_RECYCLE": showBallUI(); break;
                case "ACTION_CLOSE": stopSelf(); break;
                case "ACTION_APPS": toggleAppList(); break;
                case "ACTION_SETTINGS":
                    Intent si = new Intent(c, SettingsActivity.class);
                    si.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(si);
                    break;
                case "ACTION_EXPAND": showMainUI(); break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @android.annotation.SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        config = new ConfigManager(this);
        createNotificationChannel();
        initViews();
        showMainUI();

        IntentFilter filter = new IntentFilter();
        filter.addAction("ACTION_RECYCLE");
        filter.addAction("ACTION_CLOSE");
        filter.addAction("ACTION_APPS");
        filter.addAction("ACTION_SETTINGS");
        filter.addAction("ACTION_EXPAND");

        // API 33+ 必须显式声明导出标志，Linter 误报通过 @SuppressLint 压制
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(receiver, filter);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notif = new NotificationCompat.Builder(this, "fw_channel")
                .setContentTitle("悬浮窗服务")
                .setContentText("运行中")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(1, notif, android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE);
        } else {
            startForeground(1, notif);
        }
        return START_STICKY;
    }

    private void initViews() {
        timeView = new TimeWidget(this);
        funcView = new FunctionWidget(this);
        ballLp = createLp(Gravity.CENTER);
        ballView = new FloatingBall(this, ballLp);
        appListView = new AppListPanel(this);
    }

    public void showMainUI() {
        removeSafely(ballView);
        removeSafely(appListView);
        addView(timeView, Gravity.BOTTOM | Gravity.END, config.getInt("time_x", 20), config.getInt("time_y", -20));
        addView(funcView, Gravity.CENTER_VERTICAL | Gravity.END, config.getInt("func_x", 20), config.getInt("func_y", 0));
    }

    public void showBallUI() {
        removeSafely(timeView);
        removeSafely(funcView);
        removeSafely(appListView);
        addViewSafely(ballView, ballLp);
    }

    private void toggleAppList() {
        if (appListView.getParent() != null) {
            removeSafely(appListView);
        } else {
            WindowManager.LayoutParams lp = createLp(Gravity.CENTER_VERTICAL | Gravity.END);
            lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.6f);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            addViewSafely(appListView, lp);
        }
    }

    private WindowManager.LayoutParams createLp(int gravity) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        lp.gravity = gravity;
        return lp;
    }

    private void addView(View view, int gravity, int x, int y) {
        if (view.getParent() != null) return;
        WindowManager.LayoutParams lp = createLp(gravity);
        lp.x = x; lp.y = y;
        wm.addView(view, lp);
    }

    private void addViewSafely(View view, WindowManager.LayoutParams lp) {
        if (view.getParent() != null) return;
        wm.addView(view, lp);
    }

    private void removeSafely(View v) {
        if (v != null && v.getParent() != null) wm.removeView(v);
    }

    // minSdk=26，此检查始终为 true，直接执行
    private void createNotificationChannel() {
        NotificationChannel ch = new NotificationChannel("fw_channel", "悬浮窗", NotificationManager.IMPORTANCE_MIN);
        getSystemService(NotificationManager.class).createNotificationChannel(ch);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try { unregisterReceiver(receiver); } catch (Exception ignored) {}
        if (timeView.getParent() != null) savePos("time", timeView);
        if (funcView.getParent() != null) savePos("func", funcView);
        if (ballView.getParent() != null) savePos("ball", ballView);
        removeSafely(timeView); removeSafely(funcView); removeSafely(ballView); removeSafely(appListView);
    }

    private void savePos(String prefix, View v) {
        int[] loc = new int[2];
        v.getLocationOnScreen(loc);
        config.saveInt(prefix + "_x", loc[0]);
        config.saveInt(prefix + "_y", loc[1]);
    }

    @Nullable @Override public IBinder onBind(Intent intent) { return null; }
}