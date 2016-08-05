package com.zhy.m.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by holybible on 16/8/5.
 */
@Target(ElementType.METHOD)
public @interface PermissionGot {
    int value();
}
