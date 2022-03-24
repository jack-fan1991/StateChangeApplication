package com.stateManger;

import com.example.model.UserModel;
import com.stateManger.test.UserStateV2;
import com.stateManger.test.UserStateV3;

import junit.framework.TestCase;

import org.junit.Test;

public class UserStateTest extends TestCase {

    @Test
    public void testCreate() {
//        UserState userState =new UserState(APP_STATE_TYPE.USER_STATE);
        new UserModel();
        UserStateV2 userStatev2 =new UserStateV2(APP_STATE_TYPE.USER_STATE);
        UserStateV3 userStatev3 =new UserStateV3(APP_STATE_TYPE.USER_STATE);
        }
}