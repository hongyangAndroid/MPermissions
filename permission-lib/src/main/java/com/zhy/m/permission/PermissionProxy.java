package com.zhy.m.permission;

/**
 * Created by zhy on 16/2/21.
 */
public interface PermissionProxy<T>
{
    void grant(T source, int requestCode);

    void denied(T source, int requestCode);

    void rationale(T source, int requestCode);

    boolean needShowRationale(int requestCode);
}
