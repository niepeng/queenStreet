package com.baibutao.apps.queenstreet.activities.common;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baibutao.apps.queenstreet.R;
import com.baibutao.apps.queenstreet.androidext.QueenStreetApplication;

/**
 * @author lsb
 * 
 * @date 2012-5-29 下午10:36:46
 */
public class BaseActivity extends ActivityGroup {

	protected QueenStreetApplication queenStreetApplication;

	protected static Drawable defaultImg140;

	protected final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		queenStreetApplication = (QueenStreetApplication) this.getApplication();
		queenStreetApplication.addActivity(this);
//		defaultImg140 = getResources().getDrawable(R.drawable.default_img_140_140);
	}

	protected ProgressDialog showProgressDialog(int message) {
		String title = getString(R.string.app_name);
		String messageString = getString(message);
		return ProgressDialog.show(this, title, messageString);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isTabActivity()) {
				confirm("确认退出后街？", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						queenStreetApplication.finishAllActivities();
					}
				}, null);
				return true;
			} else {
				this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private boolean isTabActivity() {
		for (TabFlushEnum tabFlushEnum : queenStreetApplication.tabFlushEnums) {
			if (this.getClass().getName().equals(tabFlushEnum.getTabActivity().getName())) {
				return true;
			}
		}
		return false;
	}

	protected void alert(CharSequence message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog alertDialog = builder.create();
		alertDialog.setTitle(getMessageBoxTitle());
		alertDialog.setMessage(message);
		String buttonLabel = getString(R.string.app_btn_label_ok);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, buttonLabel, (Message) null);
		alertDialog.show();
	}

	protected void confirm(CharSequence message, OnClickListener onYesButton, OnClickListener onNoButton) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog alertDialog = builder.create();
		alertDialog.setTitle(getMessageBoxTitle());
		alertDialog.setMessage(message);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.app_btn_label_ok), onYesButton);
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.app_btn_label_canel), onNoButton);
		alertDialog.show();
	}

	protected String getMessageBoxTitle() {
		return this.getTitle().toString();
	}

	protected void toastShort(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	protected void toastShort(int message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	protected void toastLong(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	protected void toastLong(int message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	

	protected String getLogTag() {
		return getClass().getSimpleName();
	}

	protected final void logError(String msg, Throwable e) {
		Log.e(getLogTag(), msg, e);
	}

	protected final void logError(String msg) {
		Log.e(getLogTag(), msg);
	}

	protected final void logWarn(String msg) {
		Log.w(getLogTag(), msg);
	}

	protected final void logInfo(String msg) {
		Log.i(getLogTag(), msg);
	}

	protected final void logDebug(String msg) {
		Log.d(getLogTag(), msg);
	}
	
}