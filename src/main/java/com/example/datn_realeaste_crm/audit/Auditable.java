package com.example.datn_realeaste_crm.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    String action();
    String entityType() default "";
    String entityIdParam() default "";
    boolean logParams() default false;
    boolean logResult() default false;
}