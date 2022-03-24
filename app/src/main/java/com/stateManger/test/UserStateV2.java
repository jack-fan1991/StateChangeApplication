package com.stateManger.test;

import com.example.model.UserModel;
import com.stateManger.APP_STATE_TYPE;
import com.stateManger.test.AbstractStateV2;


public class UserStateV2 extends AbstractStateV2<UserModel> {

    public UserStateV2(APP_STATE_TYPE tag ) {
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
