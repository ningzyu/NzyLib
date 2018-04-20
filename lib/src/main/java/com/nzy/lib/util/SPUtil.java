package com.nzy.lib.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * 作者：宁震宇on 2017/11/1.
 * 邮箱：348723352@qq.com
 * 本类作用：sharedPreferences工具类(单例模式)
 */

public class SPUtil {
    private static final String SP_NAME = "param";//参数名
    private static SPUtil mSpUtils;//
    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private SPUtil(String key,Context context){
        this.context = context;
        sp = this.context.getSharedPreferences(key, Context.MODE_APPEND);
        editor = sp.edit();
    }
    public static SPUtil getInstance(Context context){
        return getInstance(context,SP_NAME);
    }
    public static SPUtil getInstance(Context context,String key) {
        if (mSpUtils == null) {
            synchronized (SPUtil.class) {
                if (mSpUtils == null) {
                    mSpUtils = new SPUtil(key,context);
                    return mSpUtils;
                }
            }
        }
        return mSpUtils;
    }

    public void putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    public boolean getBoolean(String key){
        return getBoolean(key, false);
    }
    public boolean getBoolean(String key, Boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public String getString(String key) {
        return getString(key, "");
    }
    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }
    public int getInt(String key){
        return getInt(key,0);
    }
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }
    //获得全部
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public void remove(String key) {
        sp.edit().remove(key).commit();
    }
}
