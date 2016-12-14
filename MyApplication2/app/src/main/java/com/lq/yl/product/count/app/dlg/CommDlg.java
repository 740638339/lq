package com.lq.yl.product.count.app.dlg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.util.ActStack;

/**
 * Created by wb-liuquan.e on 2016/10/27.
 */
public class CommDlg extends Dialog {

    private OnCfmOrCan onCfmOrCan;

    protected CommDlg(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
    }

    public CommDlg(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
    }

    public CommDlg(final Activity activity, String title, String tip) {
        super(activity, R.style.Comm_Dlg);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        setContentView(R.layout.conf_dlg_layout);

        TextView dlgTitle = (TextView) findViewById(R.id.dlg_title);
        TextView dlgTip = (TextView) findViewById(R.id.dlg_tip_msg);

        if (title == null || title.equals("")) {
            dlgTitle.setVisibility(View.GONE);
        } else {
            dlgTitle.setText(title);
        }

        if (tip == null || tip.equals("")) {
            dlgTip.setVisibility(View.GONE);
        } else {
            dlgTip.setText(tip);
        }


        TextView conf = (TextView) findViewById(R.id.btn_one);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActStack.getInstance().exit();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });


        TextView canc = (TextView) findViewById(R.id.btn_two);
        canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }


    public interface OnCfmOrCan {
        void confirm();

        void canc();
    }

    public void setOnCfmOrCan(OnCfmOrCan onCfmOrCan) {
        this.onCfmOrCan = onCfmOrCan;
    }


}
