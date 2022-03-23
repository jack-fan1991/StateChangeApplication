package com.example.utils;


/**
 * 單例模板
 */

public abstract class SingletonTemplate<T> {
    private T mInstance;

    protected abstract T create();

    public final T getmInstance() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }
            return mInstance;
        }
    }
}
