package com.example.annotationverification;


import com.example.annotationverification.Annotation.AppStateVerificationBindingGetter;
import com.example.annotationverification.utils.LoggerTagManger;
import com.example.annotationverification.utils.VerificationUtils;
import com.example.logger.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 泛型類須繼承提供反射equals
 */


abstract public class AbstractAnnotationEntity<T> {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * 以註解關注 get方法進行驗證
     */

    @Deprecated
    protected boolean equalsByAnnotationMethod(T t, Class clz) {
        Class<?> cls = this.getClass();
        Method[] methods = cls.getDeclaredMethods();
        boolean isEqual;
        System.out.println("===========================");
        for (Method method : methods) {
            Annotation workoutPlanVerification = method.getAnnotation(clz);
            String s = "";
            if (workoutPlanVerification != null) {
                if (method.getParameterTypes().length > 0) {
                    s = TAG + " 檢查方法 : " + method.getName() + "() " + "方法不進入驗證";
                    Logger.d(s);
                } else {
                    isEqual = false;
                    try {
                        if (VerificationUtils.isEmpty(method.invoke(this, (Object[]) null))
                                && VerificationUtils.isEmpty(method.invoke(t, (Object[]) null))
                        ) {
                            continue;
                        } else if (!VerificationUtils.isEmpty(method.invoke(this, (Object[]) null))
                                && VerificationUtils.isEmpty(method.invoke(t, (Object[]) null))
                        ) {
                            isEqual = false;
                        } else if (VerificationUtils.isEmpty(method.invoke(this, (Object[]) null))
                                && !VerificationUtils.isEmpty(method.invoke(t, (Object[]) null))
                        ) {
                            isEqual = false;
                        } else {
                            isEqual = method.invoke(this, (Object[]) null).equals(method.invoke(t, (Object[]) null));

                        }
                        s = TAG + " 檢查方法 : " + method.getName() + "() " + ", this : " + method.invoke(this, (Object[]) null) + ", target : " + method.invoke(t, (Object[]) null) + " , isEqual :" + isEqual;
                        Logger.d(s);
                        if (!isEqual) {
                            Logger.d("!!!!!!!isNotEquals!!!!!!!!!!!");
                            return false;
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        Logger.d("When exception return isNotEquals");
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.d("When exception return isNotEquals");
                        return false;
                    }
                }
            }
        }
        Logger.d("Equals");
        return true;
    }

    /**
     * 需搭配AppStateVerificationBindingGetter.class
     * 以註解獲得綁定一個getter方法於關注的setter 上並以反射對此綁定getter取值並賦值
     */
    @Deprecated
    private boolean upDateByBindingAnnotationMethodSetter(T t, Method getter, Method[] methods) {
        if (!getter.getName().startsWith("get")) {
            throw new RuntimeException(getter.getName() + "請以get開頭");
        }
        boolean isUpdate = false;
        try {
            for (Method method : methods) {
                AppStateVerificationBindingGetter setter = method.getAnnotation(AppStateVerificationBindingGetter.class);
                Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "compare method :" + method.getName() + "()");
                if (setter != null) {
                    if (!setter.bindGetter().startsWith("get")) {
                        throw new RuntimeException("setter :" + method.getName() + "()" + "Annotation to bind : " + setter.bindGetter() + "()" + "請以get開頭");
                    }
                    Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "setter :" + method.getName() + "()" + "Annotation to bind : " + setter.bindGetter() + "()" + " but is " + getter.getName() + "()");
                    if (setter.bindGetter().equals(getter.getName())) {
                        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "=======BOOOOOM that right !!");
                        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "before update : " + this.toString());
                        Object value = getter.invoke(t, (Object[]) null);
                        method.invoke(this, value);
                        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "after update : " + this.toString());
                        isUpdate = true;
                    }
                } else {
                    Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "mission filed");

                }
            }
            if (!isUpdate) {
                throw new RuntimeException("請使用綁定AppStateVerificationBindingGetter.class getter  方法");
            }
        } catch (Exception e) {
            Logger.e(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + e);
        }
        return isUpdate;
    }

    /**
     * 使用註解設定關注需要驗證的方法
     * 需搭配AppStateVerificationBindingGetter.class 關注一個getter綁定於upDateByBindingAnnotationMethodSetter()更新變量
     * 已更新可直接綁註解於城垣變量進行更新 請參照upDateIfNotEqualsByFieldAnnotation()
     */

    @Deprecated
    protected boolean upDateIfNotEqualsByAnnotationMethod(T newT, Class clz) {
        Class<?> cls = this.getClass();
        Method[] methods = cls.getDeclaredMethods();
        boolean isEqual;
        Object updateValue;
        Object target;
        boolean isUpdate = false;
        System.out.println("===========================");
        for (Method method : methods) {
            Annotation workoutPlanVerification = method.getAnnotation(clz);
            String s = "";
            if (workoutPlanVerification != null) {
                if (method.getParameterTypes().length > 0) {
                    s = LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + " 檢查方法 : " + method.getName() + "() " + "方法不進入驗證";
                    Logger.d(s);
                } else {

                    try {
                        target = method.invoke(this, (Object[]) null);
                        updateValue = method.invoke(newT, (Object[]) null);
                        if (VerificationUtils.isEmpty(target)
                                && VerificationUtils.isEmpty(updateValue)
                        ) {
                            s = LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + " 檢查方法 : " + method.getName() + "() " + ", 原始 : " + target + ", 更新 : " + updateValue;
                            Logger.d("continue =>" + s);
                            continue;
                        } else if (!VerificationUtils.isEmpty(target)
                                && VerificationUtils.isEmpty(updateValue)
                        ) {
                            isEqual = false;
                        } else if (VerificationUtils.isEmpty(target)
                                && !VerificationUtils.isEmpty(updateValue)
                        ) {
                            isEqual = false;
                        } else {
                            isEqual = method.invoke(this, (Object[]) null).equals(method.invoke(newT, (Object[]) null));

                        }
                        s = LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + " 檢查方法 : " + method.getName() + "() " + ", 原始 : " + target + ", 更新 : " + updateValue + " , isEqual :" + isEqual;

                        Logger.d(s);
                        if (!isEqual) {
                            isUpdate = upDateByBindingAnnotationMethodSetter(newT, method, methods);
                            Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "!!!!!!!isNotEquals!!!!!!!!!!!");
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {

                        Logger.e(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + e);
//                        return false;
                    }
                }
            }
        }
        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "isUpdate : " + isUpdate);
        return isUpdate;
    }

    /**
     * 搭配註解標記須關注的成員變量, 更新值回空值自動忽略
     * sample : AppStateVerification.class
     */

    protected boolean isEqualsByFieldAnnotation(T newT, Class annotationClass) {
        Class<?> cls = this.getClass();
        Field[] fields = cls.getDeclaredFields();
        boolean isEqual = true;
        Object updateValue;
        Object target;
        System.out.println("===========================" + this.getClass().getSimpleName() + ".isEqualsByFieldAnnotation()===========================");
        for (Field field : fields) {
            Annotation verificationAnnotation = field.getAnnotation(annotationClass);
            String s = "";
            if (verificationAnnotation != null) {
                try {
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    target = field.get(this);
                    updateValue = field.get(newT);
                    if (updateValue == null) {
                        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "更新變量" + field.getName() + "為空值自動忽略");
                        continue;
                    }
                    if (target == null) {
                        isEqual = false;
                    } else {
                        isEqual = target.equals(updateValue);
                    }
                    s = LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + " 檢查變量 : " + fieldName + ", 原始 : " + target + ", 發現更新 : " + updateValue + " , isEqual : " + isEqual;
                    Logger.d(s);
                    if (!isEqual) {
                        break;
                    }
                } catch (Exception e) {
                    Logger.e(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + e);
                    return false;
                }
            }
        }
        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + this.getClass().getSimpleName() + " isEqual : " + isEqual);
        return isEqual;
    }


    /**
     * 搭配註解標記須關注的成員變量
     * sample : AppStateVerification.class
     */

    protected boolean upDateByFieldAnnotation(T newT, Class clz) {
        Class<?> cls = this.getClass();
        Field[] fields = cls.getDeclaredFields();
        Object updateValue;
        Object target;
        boolean isUpdate = false;
        System.out.println("===========================" + this.getClass().getSimpleName() + ".upDateByFieldAnnotation()===========================");
        for (Field field : fields) {
            Annotation verificationAnnotation = field.getAnnotation(clz);
            String s = "";
            if (verificationAnnotation != null) {
                try {
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    target = field.get(this);
                    updateValue = field.get(newT);
                    if (target == null && updateValue == null) {
                        continue;
                    } else if (target != null && (target.equals(updateValue))) {
                        continue;
                    }
                    if (!"password".equals(fieldName)) {
                        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "[ before update ]: " + this.toString());
                    }
                    field.set(this, updateValue);
                    isUpdate = true;
                    s = LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + " 更新變量 : " + fieldName + ", 原始 : " + target + " ,更新為 => " + updateValue;
                    if (!"password".equals(fieldName)) {
                        Logger.i(s);
                        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "[ after update ]: " + this.toString());
                    }
                } catch (Exception e) {

                    Logger.e(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + e);
                    return false;
                }
            }
        }

        Logger.d(LoggerTagManger.EQUALS_BY_ANNOTATION_TRACKING + "isUpdate : " + isUpdate);
        return isUpdate;
    }
}
