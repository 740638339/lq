package com.lq.yl.product.count.app.mdl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Date;

/**
 * Created by wb-liuquan.e on 2016/11/16.
 */
public class User implements Serializable {

    private String mName = "";
    private String mPwd = "";
    private String mLastLogonTime = "" ;


    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPwd() {
        return mPwd;
    }

    public void setPwd(String mPwd) {
        this.mPwd = mPwd;
    }

    public String getLastLogonTime() {
        return mLastLogonTime;
    }

    public void setLastLogonTime(String mLastLogonTime) {
        this.mLastLogonTime = mLastLogonTime;
    }

    public static User WriteUser(User user, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("USER_SERIAL", context.MODE_PRIVATE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);
            String userBase64Str = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USER_INFO", userBase64Str);
            editor.commit();

        } catch (IOException e) {

        }
        return user;
    }

    @SuppressWarnings("static-access")
    public static User ReadUser(Context context) {
        User user = null;
        SharedPreferences preferences = context.getSharedPreferences("USER_SERIAL", context.MODE_PRIVATE);
        String userInfo = preferences.getString("USER_INFO", "");
        try {

            byte[] base64Bytes = Base64.decode(userInfo.getBytes(),
                    Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            user = (User) ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 清空User的参数
     */
    public void resetUser() {
        this.mName = "";
        this.mPwd = "";

    }
}
