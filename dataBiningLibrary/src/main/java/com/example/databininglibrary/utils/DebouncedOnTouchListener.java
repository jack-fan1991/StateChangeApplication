package com.example.databininglibrary.utils;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * A Debounced OnClickListener
 * Rejects clicks that are too close together in time.
 * This class is safe to use as an OnClickListener for multiple views, and will debounce each one separately.
 */
public abstract class DebouncedOnTouchListener implements View.OnTouchListener {

    private final long minimumInterval;
    private Map<View, Long> lastClickMap;

    /**
     * Implement this in your subclass instead of onClick
     *
     * @param v The view that was clicked
     */
    public abstract void onDebouncedTouch(View v, MotionEvent event);

    /**
     * The one and only constructor
     *
     * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
     */
    public DebouncedOnTouchListener(long minimumIntervalMsec) {
        this.minimumInterval = minimumIntervalMsec;
        this.lastClickMap = new WeakHashMap<View, Long>();
    }

    @Override
    public boolean onTouch(View touchView, MotionEvent event) {
        Long previousClickTimestamp = lastClickMap.get(touchView);
        long currentTimestamp = SystemClock.uptimeMillis();

        lastClickMap.put(touchView, currentTimestamp);
        if (previousClickTimestamp == null || (currentTimestamp - previousClickTimestamp.longValue() > minimumInterval)) {
            onDebouncedTouch(touchView, event);
        }
        return false;
    }
}