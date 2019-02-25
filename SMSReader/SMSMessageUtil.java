package com.wan.ticketmessage.Util;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xen on 2019/2/25 0025.
 */

public class SMSMessageUtil {

    private static final String SMS_URI_ALL = "content://sms/"; // 所有短信
    private static final String SMS_URI_INBOX = "content://sms/inbox"; // 收件箱
    private static final String SMS_URI_SEND = "content://sms/sent"; // 已发送
    private static final String SMS_URI_DRAFT = "content://sms/draft"; // 草稿
    private static final String SMS_URI_OUTBOX = "content://sms/outbox"; // 发件箱
    private static final String SMS_URI_FAILED = "content://sms/failed"; // 发送失败
    private static final String SMS_URI_QUEUED = "content://sms/queued"; // 待发送列表

    /**
     * 获取所有短信的内容，使用前需要获取短信读取权限
     * @param activity 传入的一个activity对象
     * @return
     */
    public static List<String> getAllSMSBody(Activity activity){
        List<String> bodyList = new ArrayList<>();
        if (PermissionUtils.checkPermission(activity, Manifest.permission.READ_SMS)) {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[] { "_id", "address", "person",
                    "body", "date", "type", };
            Cursor cur = activity.getContentResolver().query(uri, projection, null,
                    null, "date desc");
            int index_Body = cur.getColumnIndex("body");
            //Cursor下标是从-1开始的，所以要移动到first（下标为0）
            if (cur.moveToFirst()) {
                do {
                    String strbody = cur.getString(index_Body);
                    bodyList.add(strbody);
                }while (cur.moveToNext());
            }

            return bodyList;
        } else {
            Toast.makeText(activity, "未授权短信读取权限", Toast.LENGTH_SHORT).show();
            return null;
        }

    }
}
