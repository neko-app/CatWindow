package com.example.floatwindow;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.List;

public class AppListPanel extends ScrollView {
    public AppListPanel(Context ctx) {
        super(ctx);
        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(12, 12, 12, 12);
        root.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

        android.content.pm.PackageManager pm = ctx.getPackageManager();
        Intent main = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = pm.queryIntentActivities(main, 0);

        for (ResolveInfo info : apps) {
            TextView tv = new TextView(ctx);
            tv.setText(info.loadLabel(pm));
            tv.setTextColor(Color.WHITE);
            tv.setPadding(16, 16, 16, 16);
            tv.setOnClickListener(v -> {
                Intent launch = pm.getLaunchIntentForPackage(info.activityInfo.packageName);
                if (launch != null) {
                    launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(launch);
                }
            });
            root.addView(tv);
        }
        addView(root);
    }
}