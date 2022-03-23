package com.example.model;

import com.example.annotationverification.Annotation.ParamVerification;
import com.example.annotationverification.BaseAnnotationEntity;


public class UserModel extends BaseAnnotationEntity<UserModel>{
    @ParamVerification()
    public String account ="";
    @ParamVerification()
    public String password = "";
    @ParamVerification()
    public int mode = 1;

    public UserModel() {
    }

    public UserModel(String account, String password) {
        this.account = account;
        this.password = password;
    }


    public String getAccount() {
        return account;
    }


    public void setAccount(String account) {
        this.account = account;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isEqualsByFieldAnnotation(UserModel userModel) {
        return super.isEqualsByFieldAnnotation(userModel);
    }


    public boolean upDateByFieldAnnotation(UserModel userModel) {
        return super.upDateByFieldAnnotation(userModel);
    }

}
