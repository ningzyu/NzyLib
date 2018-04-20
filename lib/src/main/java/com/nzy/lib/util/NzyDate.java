package com.nzy.lib.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：宁震宇on 2018/4/19.
 * 邮箱：348723352@qq.com
 * 本类作用：
 */

public class NzyDate {
    public static String getTime(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sDateFormat.format(new Date());
    }

}
