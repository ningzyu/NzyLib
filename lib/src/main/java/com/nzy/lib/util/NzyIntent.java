package com.nzy.lib.util;

import android.content.Intent;

/**
 * 作者：宁震宇on 2018/4/19.
 * 邮箱：348723352@qq.com
 * 本类作用：
 */

public class NzyIntent {

    /**
     * startActivityForResult(intent,1);
     *
     * onActivityResult(Activity回调)
     * Uri uri= data.getData();
     * uri.getPath()
     * nzyDirectory.getAbsPath(this,uri);
     *
     *
     *
     *
     */
    public Intent openFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }




}
