package com.lq.yl.product.count.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.dao.StoreProDao;
import com.lq.yl.product.count.app.mdl.User;
import com.lq.yl.product.count.app.other.gest.UnlockGestPsdAct;
import com.lq.yl.product.count.app.util.DateUtils;

public class LogonAct extends BaseAct implements View.OnTouchListener{

    private EditText mUserPwdEdt, mUserNameEdt;

    private static LogonAct instance;
    private LinearLayout mTransLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logon_act);

        this.mTransLL = (LinearLayout) this.findViewById(R.id.transLL);
        this.mTransLL.setOnTouchListener(this);
        this.mUserNameEdt = (EditText) this.findViewById(R.id.userNameEdt);
        this.mUserPwdEdt = (EditText) this.findViewById(R.id.userPwdEdt);

        instance = this;
    }

    public void logon(View v) {
        String name = this.mUserNameEdt.getText().toString();
        String pwd = this.mUserPwdEdt.getText().toString();


        if (name.equals("admin") && pwd.equals("admin")) {
            User user = new User();
            user.setName(name);
            user.setPwd(pwd);
            user.setLastLogonTime(DateUtils.getCrtDateAndTime());
            User.WriteUser(user, this);

            startActivity(new Intent(this, UnlockGestPsdAct.class));
        } else {
            showToast("用户名或密码不正确，请重新输入！");
        }

    }

    public static LogonAct getInstance() {
        return instance;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StartAct.getInstance() != null) {
            StartAct.getInstance().finish();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ((InputMethodManager)getSystemService(LogonAct.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LogonAct.this.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return false;
    }
}
