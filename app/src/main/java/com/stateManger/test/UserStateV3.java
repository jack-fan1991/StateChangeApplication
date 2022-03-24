package com.stateManger.test;

import com.example.model.UserModel;
import com.stateManger.APP_STATE_TYPE;
import com.stateManger.test.AbstractStateV3;


public class UserStateV3 extends AbstractStateV3<UserModel> {


    public UserStateV3(APP_STATE_TYPE tag ) {
        super(tag);

    }

    @Override
    public void update(UserModel userModel) {
        if (state.isEqualsByFieldAnnotation(userModel)) return;
        setChange(true);
        state.upDateByFieldAnnotation(userModel);
        notifyChange();
    }


}
