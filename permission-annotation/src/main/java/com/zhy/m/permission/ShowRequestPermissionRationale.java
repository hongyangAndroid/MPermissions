package com.zhy.m.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhy on 16/3/2.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ShowRequestPermissionRationale
{
    int value();
}
