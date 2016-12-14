package com.lq.yl.product.count.app.other.gest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.BaseAct;
import com.lq.yl.product.count.app.ChartAct;
import com.lq.yl.product.count.app.CrtProAct;
import com.lq.yl.product.count.app.CrtStoreAct;
import com.lq.yl.product.count.app.MainAct;
import com.lq.yl.product.count.app.ProductApp;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.dao.StoreProDao;
import com.lq.yl.product.count.app.manager.ProMngAct;
import com.lq.yl.product.count.app.other.gest.view.LockPatternUtils;
import com.lq.yl.product.count.app.other.gest.view.LockPatternView;

import java.util.List;

public class UnlockGestPsdAct extends BaseAct {
	private LockPatternView mLockPatternView;
	private int mFailedPatternAttemptsSinceLastTimeout = 0;
	private CountDownTimer mCountdownTimer = null;
	private Handler mHandler = new Handler();
	private TextView mHeadTextView;
	private Animation mShakeAnim;

	private Toast mToast;

	private String mCode = "";

	private static UnlockGestPsdAct mInstance;
	private void showToast(CharSequence message) {
		if (null == mToast) {
			mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			mToast.setText(message);
		}

		mToast.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unlock_gest_psd_act_layout);

		mLockPatternView = (LockPatternView) this.findViewById(R.id.gesturepwd_unlock_lockview);
		mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
		mLockPatternView.setTactileFeedbackEnabled(true);
		mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
		mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);

		mInstance = this;

		this.mCode = getIntent().getStringExtra("code");
	}

	public static UnlockGestPsdAct getInstance(){
		return mInstance;
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!ProductApp.getInstance().getLockPatternUtils().savedPatternExists()) {
			startActivity(new Intent(this, GuideGestPsdAct.class));
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCountdownTimer != null)
			mCountdownTimer.cancel();
	}
	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mLockPatternView.clearPattern();
		}
	};

	protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

		public void onPatternStart() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
			patternInProgress();
		}

		public void onPatternCleared() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
		}

		public void onPatternDetected(List<LockPatternView.Cell> pattern) {
			if (pattern == null)
				return;
			if (ProductApp.getInstance().getLockPatternUtils().checkPattern(pattern)) {
				mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
				/*Intent intent = new Intent(UnlockGestPsdAct.this,
						GuideGestPsdAct.class);
				// 打开新的Activity
				startActivity(intent);
				showToast("解锁成功");
				finish();*/
				if(MainAct.getInstance() != null){
					if(UnlockGestPsdAct.this.mCode.equals(ProMngAct.LOCK_PSD_PRO_CODE)){
						startActivity(new Intent(UnlockGestPsdAct.this, ProMngAct.class));
					}else if(UnlockGestPsdAct.this.mCode.equals(ChartAct.LOCK_PSD_CHART_CODE)){
						startActivity(new Intent(UnlockGestPsdAct.this, ChartAct.class));
					}
				}else{
					if (new StoreProDao(UnlockGestPsdAct.this).getCount() == 0) {
						startActivity(new Intent(UnlockGestPsdAct.this, CrtStoreAct.class));
					} else if (new ProductDao(UnlockGestPsdAct.this).getCount() == 0) {
						startActivity(new Intent(UnlockGestPsdAct.this, CrtProAct.class));
					} else {
						startActivity(new Intent(UnlockGestPsdAct.this, MainAct.class));
					}
				}
			} else {
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Wrong);
				if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
					mFailedPatternAttemptsSinceLastTimeout++;
					int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
							- mFailedPatternAttemptsSinceLastTimeout;
					if (retry >= 0) {
						if (retry == 0)
							showToast("您已5次输错密码，请30秒后再试");
						mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
						mHeadTextView.setTextColor(Color.RED);
						mHeadTextView.startAnimation(mShakeAnim);
					}

				}else{
					showToast("输入长度不够，请重试");
				}

				if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
					mHandler.postDelayed(attemptLockout, 2000);
				} else {
					mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
				}
			}
		}

		public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

		}

		private void patternInProgress() {
		}
	};
	Runnable attemptLockout = new Runnable() {

		@Override
		public void run() {
			mLockPatternView.clearPattern();
			mLockPatternView.setEnabled(false);
			mCountdownTimer = new CountDownTimer(
					LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
					if (secondsRemaining > 0) {
						mHeadTextView.setText(secondsRemaining + " 秒后重试");
					} else {
						mHeadTextView.setText("请绘制手势密码");
						mHeadTextView.setTextColor(Color.WHITE);
					}

				}

				@Override
				public void onFinish() {
					mLockPatternView.setEnabled(true);
					mFailedPatternAttemptsSinceLastTimeout = 0;
				}
			}.start();
		}
	};

}
