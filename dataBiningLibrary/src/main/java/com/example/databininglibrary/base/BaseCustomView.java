package com.example.databininglibrary.base;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import java.util.Map;
import java.util.WeakHashMap;


/**
 * 可將此子類實現設定onTouch監聽後透過依賴 ICustomViewActionListener 進行事件轉拋
 */


public abstract class BaseCustomView<DATA_BINDING extends ViewDataBinding, VIEW_MODEL extends BaseCustomViewModel> extends LinearLayout implements ICustomView<VIEW_MODEL>, View.OnTouchListener {
    private DATA_BINDING dataBinding;
    private VIEW_MODEL viewModel;
    private ICustomViewActionListener mListener;

    private int minimumInterval = 2000;
    private Map<View, Long> lastClickMap = new WeakHashMap<View, Long>();


    public BaseCustomView(Context context) {
        super(context);
        setClickable(true);
        init(context);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    protected  boolean isTouch(View v, MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        float viewWidth = v.getWidth();
        float viewHeight = v.getHeight();
        return (eventX >= 0 && eventX <= viewWidth) &&
                (eventY >= 0 && eventY <= viewHeight);
    }

    /**
     * 最小重複觸碰事件間隔時間
     */
    protected void setTouchMinInterval(int minimumInterval) {
        this.minimumInterval = minimumInterval;
    }

    public View getRootView() {
        return dataBinding.getRoot();
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dataBinding = DataBindingUtil.inflate(inflater, viewLayoutId(), this, false);
        if (viewLayoutId() != 0) {
            dataBinding.setLifecycleOwner((LifecycleOwner) context);
            dataBinding.getRoot().setClickable( rootClickable());
            dataBinding.getRoot().setOnTouchListener(this);
            this.addView(dataBinding.getRoot());
            setTouchListener();
        }
        onInitDone();
    }

    protected abstract void onInitDone();


    public VIEW_MODEL getViewModel() {
        return viewModel;
    }

    @Override
    public void setActionListener(ICustomViewActionListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 攔截onTouch 統一處理isTouch 並由 ICustomViewActionListener 轉發
     * !!轉發回調當前viewModel狀態 !!
     */
    @Override
    public boolean onTouch(View touchView, MotionEvent event) {
        if (mListener == null) {
            return false;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isTouch(touchView, event)) {
                Long previousClickTimestamp = lastClickMap.get(touchView);
                long currentTimestamp = SystemClock.uptimeMillis();
                lastClickMap.put(touchView, currentTimestamp);

                //連續觸碰攔截
                if (previousClickTimestamp == null || (currentTimestamp - previousClickTimestamp.longValue() > minimumInterval)) {
                    //用來監聽觸發事件同時回調當前UI data
                    mListener.onAction(ICustomViewActionListener.ACTION_VIEW_TOUCH, touchView, viewModel);
                }
                onRootViewTouch(touchView);
            }
        }
        return false;
    }

    @Override
    public void setData(VIEW_MODEL data) {
        viewModel = data;
//        setDataToView(data);
        if (dataBinding != null) {
            setDataToView(data);
//            dataBinding.executePendingBindings();
        }
        onDataUpdate();
    }


    protected DATA_BINDING getDataBinding() {
        return dataBinding;
    }

    protected void onDataUpdate() {

    }


    /**
     * 用來設定需要監聽的元件
     * 才能從setActionListener()統一回調
     *
     * Sample :
     * getDataBinding().ivRecommendSegBg.setOnTouchListener(this);
     * getDataBinding().btnStart.setOnTouchListener(this);
     * getDataBinding().btnRightArrow.setOnTouchListener(this);
     * getDataBinding().btnLeftArrow.setOnTouchListener(this);
     */
    protected abstract void setTouchListener();

    protected abstract int viewLayoutId();

    /**
     * 父類無法得知子類於 xml 定義的  variable Name 交由子類進行注入
     * 調用此方法進行畫面更新
     * Sample :
     * <layout ....
     * <variable
     * name="viewModel"
     * type="com.gsh.biofit2.pages.modeSelectActivity.dialog.QuestionDialogViewModel" />
     */

    protected abstract void setDataToView(VIEW_MODEL viewModel);

    protected abstract void onRootViewTouch(View v);

    protected boolean rootClickable(){
        return  true;
    };


}
