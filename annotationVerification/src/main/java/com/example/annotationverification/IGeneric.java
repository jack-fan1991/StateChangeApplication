package com.example.annotationverification;

import com.example.logger.Logger;

import java.lang.reflect.ParameterizedType;

public interface IGeneric<T> {
    /**
     * 使用泛動態產生state state必須有無參建構子
     * T 泛型類型如有父類別則需調用super()方法否則會判定為找不到無參建構子
     * @return
     */
//
    default T genericCreate(){
        try {
            return  (T) ((Class) ((ParameterizedType) this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        Logger.d("");
        return null;
    }
}
