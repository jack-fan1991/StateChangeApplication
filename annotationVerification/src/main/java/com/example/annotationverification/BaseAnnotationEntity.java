package com.example.annotationverification;


import com.example.annotationverification.Annotation.ParamVerification;

/**泛型類須繼承提供反射equals
 * 預設查找 @ParamVerification註解的成員變量
 * 使用自定義關注可客製化更新
 * 可自訂義註解進行標註
 * */


public class BaseAnnotationEntity<T> extends AbstractAnnotationEntity<T> implements Cloneable{
    /**
     * 搭配註解標記須關注的成員變量
     * 預設為對帶有註解 ParamVerification.class 的變量
     */

    public boolean isEqualsByFieldAnnotation(T t) {
       return super.isEqualsByFieldAnnotation(t, ParamVerification.class);
    }
    /**
     * 搭配註解標記須關注的成員變量
     * 預設為對帶有註解 ParamVerification.class 的變量
     */

    public boolean upDateByFieldAnnotation(T t) {
        return super.upDateByFieldAnnotation(t,  ParamVerification.class);
    }
    /**
     * 自定義關注註解
     */

    public boolean isEqualsByFieldAnnotation(T t ,Class annotationClass) {
        return super.isEqualsByFieldAnnotation(t, annotationClass);
    }

    /**
     * 自定義關注註解
     */

    public boolean upDateByFieldAnnotation(T t,Class annotationClass) {
        return super.upDateByFieldAnnotation(t,  annotationClass);
    }


    public T cloneEntity() throws CloneNotSupportedException {
        return  (T)super.clone();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
