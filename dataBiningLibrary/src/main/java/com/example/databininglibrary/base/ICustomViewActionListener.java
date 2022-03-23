package com.example.databininglibrary.base;

import android.view.View;

public interface ICustomViewActionListener {
    String ACTION_VIEW_TOUCH ="ACTION_VIEW_TOUCH";
    /**
     * @param action: 點擊事件
     * @param view : 點擊視圖
     * @param viewModel: 當前狀態
     */

    void onAction(String  action,View view,BaseCustomViewModel viewModel);

}
