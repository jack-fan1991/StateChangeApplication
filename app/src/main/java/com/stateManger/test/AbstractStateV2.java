package com.stateManger.test;

import com.example.annotationverification.BaseAnnotationEntity;
import com.example.annotationverification.utils.LoggerTagManger;
import com.example.logger.Logger;
import com.example.utils.DebugUtils;
import com.stateManger.APP_STATE_TYPE;
import com.stateManger.BaseOnStateChangeListener;
import com.stateManger.StateEntity;

import java.lang.reflect.ParameterizedType;

/**  泛型類可繼承AbstractAnnotationEntity 提供反射equals 方法及反射更新方法
 *   使用泛動態產生state state必須有無參建構子
 * */


public abstract  class AbstractStateV2<T extends BaseAnnotationEntity<T>>   {

    private boolean isDirty =false;
    protected BaseOnStateChangeListener listener;
    protected APP_STATE_TYPE tag ;
    protected T state;
    protected StateEntity stateEntity =new StateEntity();

    public AbstractStateV2(APP_STATE_TYPE tag) {
        this.tag =tag;
        genericCreate();
    }


    protected void genericCreate() {
        try {
            state =  (T) ((Class) ((ParameterizedType) this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        Logger.d("");


    };

    public void setListener(BaseOnStateChangeListener listener) {
        this.listener = listener;
    }
    //由子類決定是否需要更新及通知
    public void update(T t) {

    }

    /** 對此回傳值做狀態更改時會改變同地址值,要觸發更新通知請使用cloneState()後再進行update
     *
     */
    public T getState(){
        return this.state;
    }

    /**提供一個方便的複製涵式
     *
     */
    public T cloneState(){
        T t =null;
        try {
            t = this.state.cloneEntity();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return t;
    }

    protected  void  setChange(boolean isChange){
        String s ;
        if(isChange){
            s ="[ 標記為需要通知 ] ";
        }else{
            s="[ 標記為不需要通知 ] ";
        }
        Logger.d(LoggerTagManger.APP_SETTING_MANGER_TRACKING + s+ DebugUtils.listenerTrace(listener,this) );
        this.isDirty =isChange;
    }

    protected synchronized void  notifyChange(){
        if ( !isDirty) {
            Logger.d(LoggerTagManger.APP_SETTING_MANGER_TRACKING + DebugUtils.listenerTrace(listener,this) +"不發出通知");
        }
        if (listener != null) {
            Logger.d(LoggerTagManger.APP_SETTING_MANGER_TRACKING + DebugUtils.listenerTrace(listener,this));
            stateEntity.setTag(tag).setEntity(this.state);
            listener.onStateChangeListener( stateEntity );
        }
    }



}




