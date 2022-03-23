package com.stateManger;

import com.example.model.UserModel;


public class UserState extends AbstractState<UserModel> {

    @Override
    protected UserModel create() {
        return new UserModel();
    }

    UserState(APP_STATE_TYPE tag ) {
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
