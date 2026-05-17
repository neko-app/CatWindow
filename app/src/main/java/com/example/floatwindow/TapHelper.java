package com.example.floatwindow;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;

public class TapHelper {
    private int tapCount = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable resetRunnable = () -> tapCount = 0;
    private final OnMultiTapListener listener;

    public interface OnMultiTapListener {
        void onDoubleTap();
        void onTripleTap();
    }

    public TapHelper(OnMultiTapListener listener) {
        this.listener = listener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            tapCount++;
            handler.removeCallbacks(resetRunnable);
            handler.postDelayed(resetRunnable, 450);

            if (tapCount == 2) {
                listener.onDoubleTap();
                return true;
            } else if (tapCount >= 3) {
                listener.onTripleTap();
                tapCount = 0;
                handler.removeCallbacks(resetRunnable);
                return true;
            }
        }
        return false;
    }
}