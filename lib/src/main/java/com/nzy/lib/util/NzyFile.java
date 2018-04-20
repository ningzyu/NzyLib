package com.nzy.lib.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * 作者：宁震宇on 2018/4/19.
 * 邮箱：348723352@qq.com
 * 本类作用：
 */

public class NzyFile {

    /**
     * 保存文件：如果没有会自动创建，如果有的话会覆盖。 当在创建时加入true参数，回实现对文件的续写。 false则会覆盖前面的数据
     */
    public static void bufferSave(String msg,File logFile){
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(logFile, true));
            bfw.write(msg);
            bfw.newLine();
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDeviceID(String str,File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readDeviceID(File file) {
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            Reader in = new BufferedReader(isr);
            int i;
            while ((i = in.read()) > -1) {
                buffer.append((char) i);
            }
            in.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
