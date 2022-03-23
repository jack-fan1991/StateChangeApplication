package com.stateManger;

import com.example.annotationverification.utils.LoggerTagManger;
import com.example.logger.Logger;
import com.example.model.UserModel;

import org.junit.Test;

public class AppSateChangeMangerTest implements BaseOnStateChangeListener {



    @Test
    public void testUserState() {
        AppSateChangeManger appSateChangeManger = AppSateChangeManger.getInstance();
        appSateChangeManger.init();
        appSateChangeManger.registerObserver(this);
        UserModel userModel = new UserModel();
        userModel.setAccount("jack");
        appSateChangeManger.update(userModel);
        UserModel userModel2 = new UserModel();
        userModel2.setAccount("");
        appSateChangeManger.update(userModel2);
        UserModel userModel3 = new UserModel();
        userModel3.setAccount("Yiting");
        appSateChangeManger.update(userModel3);
        UserModel userModel4 = new UserModel();
        userModel4.setAccount("AB");
        appSateChangeManger.update(userModel4);
        UserModel userModel5 = new UserModel();
        userModel5.setAccount("");
        appSateChangeManger.update(userModel5);
        UserModel userModel6 = new UserModel();
        userModel6.setAccount("user3");
        appSateChangeManger.update(userModel6);
        appSateChangeManger.unRegisterObserver(this);

    }

    @Test
    public void testUserStateAppStateVerificationAnnotation() {
        AppSateChangeManger appSateChangeManger = AppSateChangeManger.getInstance();
        appSateChangeManger.init();
        appSateChangeManger.registerObserver(this);
        UserModel userModel = new UserModel();
        userModel.setAccount("jack");
        appSateChangeManger.update(userModel);
        UserModel userModel2 = new UserModel();
        userModel2.setAccount("jack");
        userModel2.setMode(5);
        Logger.d(LoggerTagManger.APP_SETTING_MANGER_TRACKING + this.getClass() + "改變mode");
        appSateChangeManger.update(userModel2);
        UserModel userModel3 = new UserModel();
        userModel3.setAccount("Yiting");
        appSateChangeManger.update(userModel3);
        UserModel userModel4 = new UserModel();
        userModel4.setAccount("AB");
        appSateChangeManger.update(userModel4);
        UserModel userModel5 = new UserModel();
        userModel5.setAccount("");
        appSateChangeManger.update(userModel5);
        UserModel userModel6 = new UserModel();
        userModel6.setAccount("user3");
        appSateChangeManger.update(userModel6);
        appSateChangeManger.unRegisterObserver(this);

    }

    //    @Override
//    public void onStateChangeListener(Object tag, Object o) {
//        Logger.d(LoggerTagManger.APP_SETTING_MANGER +"觸發"+ this.getClass());
//    }
    @Override
    public void onStateChangeListener(StateEntity stateEntity) {

        switch (stateEntity.tag) {
            case USER_STATE:
                String account = ((UserModel) stateEntity.entity).getAccount();
                if ("".equals(account)) {
                    Logger.d("有人登出了");
                } else {
                    Logger.d(account + "登入了" +  ((UserModel) stateEntity.entity).toString());

                }
                break;
        }

    }
//
//    @Override
//    public void onStateChangeListener(Object tag, RingoalSetting.RingoalStateEntity RingoalStateEntity) {
//
//    }
//
//    @Override
//    public void onStateChangeListener(Object tag, WorkoutPlanEntity workoutPlanEntity) {
//
//    }
    //    @Override
//    public void onAppSettingChanged() {
//        Logger.d(LoggerTagManger.APP_SETTING_MANGER +"觸發"+ this.getClass());
//    }

//    @Override
//    public void onRingoalSettingChanged(RingoalSetting.RingoalStateEntity RingoalStateEntity) {
//        Logger.d(LoggerTagManger.APP_SETTING_MANGER +"觸發"+ this.getClass()+RingoalStateEntity.flavor);
//    }
//
//    @Override
//    public void OnWorkoutModeSettingChanged(WorkoutPlanEntity workoutPlanEntity) {
//        Logger.d(LoggerTagManger.APP_SETTING_MANGER +"觸發"+ this.getClass()+workoutPlanEntity.getMode());
//    }


}