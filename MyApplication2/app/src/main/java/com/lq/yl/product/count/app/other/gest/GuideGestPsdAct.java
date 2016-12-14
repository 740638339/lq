package com.lq.yl.product.count.app.other.gest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.BaseAct;
import com.lq.yl.product.count.app.ProductApp;

public class GuideGestPsdAct extends BaseAct {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_gest_psd_act_layout);
		findViewById(R.id.gesturepwd_guide_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProductApp.getInstance().getLockPatternUtils().clearLock();
				Intent intent = new Intent(GuideGestPsdAct.this,
						CrtGestPsdAct.class);
				// 打开新的Activity
				startActivity(intent);
				finish();
			}
		});
	}

}
