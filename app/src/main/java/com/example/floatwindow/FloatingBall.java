package com.example.floatwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import androidx.appcompat.widget.AppCompatImageView;

public class FloatingBall extends AppCompatImageView {
    private int lastX, lastY;
    private WindowManager.LayoutParams lp;
    private final TapHelper tapHelper;
    private float downX, downY;
    private long downTime;

    public FloatingBall(Context ctx, WindowManager.LayoutParams lp) {
        super(ctx);
        this.lp = lp;
        tapHelper = new TapHelper(new TapHelper.OnMultiTapListener() {
            @Override
            public void onDoubleTap() {
                getContext().sendBroadcast(new Intent("ACTION_EXPAND"));
            }
            @Override
            public void onTripleTap() {
            }
        });
        initView();
    }

    public FloatingBall(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        tapHelper = new TapHelper(new TapHelper.OnMultiTapListener() {
            @Override
            public void onDoubleTap() {
                getContext().sendBroadcast(new Intent("ACTION_EXPAND"));
            }
            @Override
            public void onTripleTap() {
            }
        });
        initView();
    }

    private void initView() {
        setImageResource(android.R.drawable.ic_menu_compass);
        setBackgroundColor(Color.BLACK);
        setAlpha(0.85f);
        setClickable(true); // 启用点击状态
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 先交给 TapHelper 处理双击/三击
        boolean handled = tapHelper.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                downTime = SystemClock.uptimeMillis();
                lastX = (int) downX;
                lastY = (int) downY;
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getRawX() - lastX);
                int dy = (int) (event.getRawY() - lastY);
                if (lp != null && (Math.abs(dx) > 2 || Math.abs(dy) > 2)) {
                    lp.x += dx;
                    lp.y += dy;
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                            .updateViewLayout(this, lp);
                }
                break;

            case MotionEvent.ACTION_UP:
                // 判断是否为点击（移动距离小 + 时间短）
                float moveX = Math.abs(event.getRawX() - downX);
                float moveY = Math.abs(event.getRawY() - downY);
                long duration = SystemClock.uptimeMillis() - downTime;
                if (moveX < 10 && moveY < 10 && duration < 300) {
                    // 触发点击：调用 performClick 以支持无障碍
                    performClick();
                }
                break;
        }
        return true; // 消费事件
    }

    @Override
    public boolean performClick() {
        // 调用父类以触发 OnClickListener（如有）
        super.performClick();
        // 手动触发 TapHelper 的单击逻辑（如需扩展）
        return true;
    }
}