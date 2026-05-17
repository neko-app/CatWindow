package com.example.floatwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

public class FunctionWidget extends LinearLayout {
    private final TapHelper tapHelper;
    private final Context ctx;

    public FunctionWidget(Context ctx) {
        super(ctx);
        this.ctx = ctx;
        setOrientation(VERTICAL);
        setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        setPadding(6, 6, 6, 6);

        tapHelper = new TapHelper(new TapHelper.OnMultiTapListener() {
            @Override
            public void onDoubleTap() {
                send("ACTION_RECYCLE");
            }

            @Override
            public void onTripleTap() {
                send("ACTION_CLOSE");
            }
        });

        addBtn("回收", v -> send("ACTION_RECYCLE"));
        addBtn("关闭", v -> send("ACTION_CLOSE"));
        addBtn("应用", v -> send("ACTION_APPS"));
        addBtn("设置", v -> send("ACTION_SETTINGS"));
    }

    private void addBtn(String text, OnClickListener listener) {
        Button b = new Button(ctx);
        b.setText(text);
        b.setTextColor(Color.WHITE);
        b.setBackgroundColor(Color.TRANSPARENT);
        b.setPadding(16, 20, 16, 20);
        b.setOnClickListener(listener);
        addView(b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return tapHelper.onTouchEvent(e) || super.onTouchEvent(e);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void send(String action) {
        ctx.sendBroadcast(new Intent(action));
    }
}