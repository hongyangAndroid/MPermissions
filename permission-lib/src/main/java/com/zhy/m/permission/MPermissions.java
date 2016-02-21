package com.zhy.m.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by namee on 2015. 11. 17..
 * <br/>
 * modified by hongyangAndroid 2016.02.21
 */
public class MPermissions
{
    private static final String SUFFIX = "$$PermissionProxy";

    public static void requestPermissions(Activity object, int requestCode, String... permissions)
    {
        _requestPermissions(object, requestCode, permissions);
    }

    public static void requestPermissions(Fragment object, int requestCode, String... permissions)
    {
        _requestPermissions(object, requestCode, permissions);
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    private static void _requestPermissions(Object object, int requestCode, String... permissions)
    {
        if (!Utils.isOverMarshmallow())
        {
            doExecuteSuccess(object, requestCode);
            return;
        }
        List<String> deniedPermissions = Utils.findDeniedPermissions(Utils.getActivity(object), permissions);

        if (deniedPermissions.size() > 0)
        {
            if (object instanceof Activity)
            {
                ((Activity) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (object instanceof Fragment)
            {
                ((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else
            {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported!");
            }

        } else
        {
            doExecuteSuccess(object, requestCode);
        }
    }


    private static void doExecuteSuccess(Object activity, int requestCode)
    {
        Class clazz = activity.getClass();
        try
        {
            Class injectorClazz = Class.forName(clazz.getName() + SUFFIX);
            PermissionProxy instance = (PermissionProxy) injectorClazz.newInstance();
            instance.grant(activity, requestCode);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private static void doExecuteFail(Object activity, int requestCode)
    {
        Class clazz = activity.getClass();
        try
        {
            Class injectorClazz = Class.forName(clazz.getName() + SUFFIX);
            PermissionProxy instance = (PermissionProxy) injectorClazz.newInstance();
            instance.denied(activity, requestCode);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions,
                                                  int[] grantResults)
    {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions,
                                                  int[] grantResults)
    {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    private static void requestResult(Object obj, int requestCode, String[] permissions,
                                      int[] grantResults)
    {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++)
        {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
            {
                deniedPermissions.add(permissions[i]);
            }
        }
        if (deniedPermissions.size() > 0)
        {
            doExecuteFail(obj, requestCode);
        } else
        {
            doExecuteSuccess(obj, requestCode);
        }
    }
}
