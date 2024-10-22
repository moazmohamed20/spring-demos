package com.roma.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE,
    ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE })
public @interface Hack {

  String value() default "";

}