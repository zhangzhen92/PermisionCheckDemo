package com.example.zz.permisioncheckdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：android 6.0 动态权限管理类
 * 创建人：zz
 * 创建时间： 2017/7/10 15:34
 */


public class PermissionUtils {

    private static PermissionUtils permissionUtils;
    public static int REQUEST_CODE = 100;                               //请求码
    private static List<String> checkPermissions = new ArrayList<>();    //被检测的权限的集合

    private static Activity mContext;

    public PermissionUtils(Context context) {
     mContext = (Activity) context;
    }

    /**
     * 需要申请的权限
     */
    public static String[] photosPermissions = new String[]{
            Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static PermissionUtils getInstance(Context context){
        if(permissionUtils == null){
            permissionUtils = new PermissionUtils(context);
        }
        return permissionUtils;
    }


    /**
     * 检测权限是否已被申请
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermissions(Context context,String ... permissions){
        mContext = (Activity)context;
        if(checkPermissions.size() != 0){
            checkPermissions.clear();
        }
        for(String premission : permissions){
            if(!checkPremission(premission)){
                //权限未申请
                checkPermissions.add(premission);
            }
        }
        if(checkPermissions.size() == 0){
            //权限全部被申请
            return true;
        }else {
            //权限没有全部被申请
            getAllPermissions(checkPermissions.toArray(new String[checkPermissions.size()]));
            return false;
        }
    }

    /**
     * 动态的申请所有的权限
     */
    private static void getAllPermissions(String[] permissions) {
        ActivityCompat.requestPermissions( mContext, permissions, REQUEST_CODE);//请求开启权限
    }


    /**
     * 检测权限是否申请了
     * @param premission
     * @return
     */
    private static boolean checkPremission(String premission) {
        int checkSelfPermission = ContextCompat.checkSelfPermission(mContext, premission);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }
}
