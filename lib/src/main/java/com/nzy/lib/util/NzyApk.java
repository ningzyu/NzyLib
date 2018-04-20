package com.nzy.lib.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

/**
 * 作者：宁震宇on 2018/4/19.
 * 邮箱：348723352@qq.com
 * 本类作用：
 */

public class NzyApk {
    /**
     * 下载的apk和当前程序版本比较
     */
    public static boolean compare(String path ,Context context) {
        PackageInfo apkInfo = getApkInfo(context,path);
        if (apkInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackage, 0);
            if (apkInfo.packageName.equals(localPackage)) {
                if (apkInfo.versionCode != packageInfo.versionCode) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取PackageInfo
     */
    public static PackageInfo getApkInfo(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info;
        }
        return null;
    }

    //普通安装方式
    public static void startInstall(Uri uri,Context context) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            versionName  = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    /**
     * 返回当前程序版本号
     */
    public static int getAppVersionCode(Context context) {
        int versionCode =0;
        try {
            versionCode  = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            return versionCode;
        } catch (Exception e) {
        }
        return versionCode;
    }
}
