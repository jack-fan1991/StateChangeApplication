package com.example.annotationverification.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 綁訂一個get方法用來更新物件
 *
 */
@Deprecated
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AppStateVerificationBindingGetter {
 String bindGetter();
}
