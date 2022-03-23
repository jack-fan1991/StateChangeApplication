package com.stateManger;

import com.example.annotationverification.utils.LoggerTagManger;
import com.example.logger.Logger;
import com.example.model.UserModel;
import com.example.utils.DebugUtils;
import com.example.utils.SingletonTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * "觀察者模式"
 * 於init()新增需要及時通知的狀態類
 * AppSateChangeManger 由registerObserver 註冊並管理需要分發通知的對向於 stateListeners
 * 註冊者時做 BaseOnStateChangeListener 回調統一回傳至 onStateChangeListener() 由 StateEntity tag 判斷改變的狀態
 * 會由update 判斷是否有要處理的狀態有責對stateListeners一一發送通知
 *
 * */

public class AppSateChangeManger implements BaseOnStateChangeListener {

    //放置需要監聽APP狀態改變的物件
    private ArrayList<BaseOnStateChangeListener> externalListeners = new ArrayList<>();
    //管理監聽狀態
    private Map<APP_STATE_TYPE, AbstractState> stateListeners = new HashMap<>();

    private static final SingletonTemplate<AppSateChangeManger> INSTANCE = new SingletonTemplate<AppSateChangeManger>() {
        @Override
        protected AppSateChangeManger create() {
            return new AppSateChangeManger();
        }
    };

    public static AppSateChangeManger getInstance() {
        return INSTANCE.getmInstance();
    }

    private  AbstractState getState(APP_STATE_TYPE app_state_type){
        return stateListeners.get(app_state_type);
    }

    public UserState getUserState() {
        return (UserState) getState(APP_STATE_TYPE.USER_STATE);
    }


    public void init() {
        UserState mUserState =new UserState(APP_STATE_TYPE.USER_STATE);
        //以AppSettingManger 為監聽者
        mUserState.setListener(this);
        stateListeners.put(APP_STATE_TYPE.USER_STATE ,mUserState);
    }

        public void update(UserModel userModel) {
        UserState state = getUserState();
        if (state != null) {
            state.update(userModel);
        }

    }


    public void close() {
        externalListeners.clear();
        Iterator<Map.Entry<APP_STATE_TYPE, AbstractState>> it = stateListeners.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<APP_STATE_TYPE, AbstractState> state = it.next();
            state.getValue().setListener(null);
            Logger.d("移除監聽"+state.getKey() +"狀態");
        }
        Logger.d("移除完成 監聽者 :"+ externalListeners +"監聽狀態 : "+ stateListeners.size());

//        mWorkoutModeSetting.setListener(null);
//        mRingoalSetting.setListener(null);

    }


    /**
     * 註冊
     *
     * @param observer 觀察者(Activity/Fragment)
     */
    public void registerObserver(BaseOnStateChangeListener observer) {
        externalListeners.add(observer);
    }

    /**
     * 解除註冊
     *
     * @param observer 觀察者(Activity/Fragment)
     */
    public void unRegisterObserver(BaseOnStateChangeListener observer) {
        externalListeners.remove(observer);
    }

    /**
     * 對外分發通知
     *
     */
    @Override
    public void onStateChangeListener(StateEntity stateEntity) {
        for (BaseOnStateChangeListener listener : externalListeners) {
            Logger.d(LoggerTagManger.APP_SETTING_MANGER_TRACKING + DebugUtils.listenerTrace(listener, this));
            listener.onStateChangeListener(stateEntity);
    }

//    @Override
//    public void onStateChangeListener(Object tag, Object o) {
//        for (BaseOnStateChangeListener listener : listeners) {
//            Logger.d(LoggerTagManger.APP_SETTING_MANGER + DebugUtills.listenerTrace(listener, this));
//            listener.onStateChangeListener(tag, o);
////        }
//        }

//    /**
//     * 通知所有人RingoalSetting 發生改變
//     * 由這此統一管理決定要不要在不同狀態下進行額外通知
//     * 如果有改變則通知
//     */
//    @Override
//    public void onRingoalSettingChanged(RingoalSetting.RingoalStateEntity RingoalStateEntity) {
//        for (OnAppSettingChangedListener listener : listeners) {
//            Logger.d(LoggerTagManger.APP_SETTING_MANGER + DebugUtills.listenerTrace(listener, this));
//            listener.onRingoalSettingChanged(RingoalStateEntity);
//        }
//    }
//
//    /**
//     * 通知所有人WorkoutModeSetting 發生改變
//     * 由這此統一管理決定要不要在不同狀態下進行額外通知
//     * 如果有改變則通知
//     */
//    @Override
//    public void OnWorkoutModeSettingChanged(WorkoutPlanEntity workoutPlanEntity) {
//        for (OnAppSettingChangedListener listener : listeners) {
//            Logger.d(LoggerTagManger.APP_SETTING_MANGER + DebugUtills.listenerTrace(listener, this));
//            listener.OnWorkoutModeSettingChanged(workoutPlanEntity);
//        }
    }


}
