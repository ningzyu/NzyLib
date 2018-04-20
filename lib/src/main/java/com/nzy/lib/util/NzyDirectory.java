package com.nzy.lib.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 作者：宁震宇on 2018/4/19.
 * 邮箱：348723352@qq.com
 * 本类作用：
 */

public class NzyDirectory {
    public static final String SD_Directory= Environment.getExternalStorageDirectory().getPath();
    /**
     * static File getExternalStorageDirectory() 获得外部存储媒体目录。（/mnt/sdcard or /storage/sdcard0）
     * static File getDataDirectory() 获得data的目录（/data）
     * static File getDownloadCacheDirectory() 获得下载缓存目录。（/cache）
     * static File getRootDirectory() 获得系统主目录（/system）
     * context.getCacheDir() 获取应用程序自己的缓存目录
     * context.getExternalCacheDir() 获取应用程序在外部存储的存储目录
     */
    //获得公共目录的下载文件
    public static String getPublicDownload(String fileName) {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).
                getAbsolutePath() + File.separator + fileName;
    }
    /**
     *  Android标准目录的路径
     *  DIRECTORY_ALARMS 系统提醒铃声存放的标准目录。
     *  DIRECTORY_DCIM 相机拍摄照片和视频的标准目录。
     *  DIRECTORY_DOWNLOADS 下载的标准目录。
     *  DIRECTORY_MOVIES 电影存放的标准目录。
     *  DIRECTORY_MUSIC 音乐存放的标准目录。
     *  DIRECTORY_NOTIFICATIONS 系统通知铃声存放的标准目录。
     *  DIRECTORY_PICTURES 图片存放的标准目录
     *  DIRECTORY_PODCASTS 系统广播存放的标准目录。
     *  DIRECTORY_RINGTONES 系统铃声存放的标准目录
     */
    //获得下载目录文件夹名
    public static String getTypeDownload(String fileName){
        return Environment.DIRECTORY_DOWNLOADS;
    }



    /**
     * 获取外部存储状态
     * MEDIA_BAD_REMOVAL 在没有挂载前存储媒体已经被移除。
     * MEDIA_CHECKING 正在检查存储媒体。
     * MEDIA_MOUNTED 存储媒体已经挂载，并且挂载点可读/写。
     * MEDIA_MOUNTED_READ_ONLY 存储媒体已经挂载，挂载点只读。
     * MEDIA_NOFS 存储媒体是空白或是不支持的文件系统。
     * MEDIA_REMOVED 存储媒体被移除。
     * MEDIA_SHARED 存储媒体正在通过USB共享。
     * MEDIA_UNMOUNTABLE 存储媒体无法挂载。
     * MEDIA_UNMOUNTED 存储媒体没有挂载。*/


    public boolean getState(){
        return MEDIA_MOUNTED.equals(Environment.getExternalStorageState())|| !Environment.isExternalStorageRemovable();
    }

    public String getAbsPath(final Context context, final Uri uri) {
        String path;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
            path = getPath(context, uri);
        } else {//4.4以下下系统调用方法
            path = getRealPathFromURI(context,uri);
        }
        return path;
    }
    public String getRealPathFromURI(Context context,Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }


    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
