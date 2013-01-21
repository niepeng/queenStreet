package com.baibutao.apps.queenstreet.tasks;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.baibutao.apps.queenstreet.activities.NavigationActivity;
import com.baibutao.apps.queenstreet.activities.UpdateClientActivity;
import com.baibutao.apps.queenstreet.activities.common.BaseActivity;
import com.baibutao.apps.queenstreet.androidext.QueenStreetApplication;
import com.baibutao.apps.queenstreet.config.Config;
import com.baibutao.apps.queenstreet.remote.RemoteManager;
import com.baibutao.apps.queenstreet.remote.Request;
import com.baibutao.apps.queenstreet.remote.Response;
import com.baibutao.apps.queenstreet.util.JsonUtil;

/**
 * @author lsb
 *
 * @date 2012-5-30 上午10:30:10
 */
public class CheckUpdateTask implements Runnable {
	private Handler handler;
	
	private String lastAndroidClientUrl;
	
	private QueenStreetApplication queenStreetApplication;
	
	private BaseActivity baseActivity;
	
	private MessageHelper messageHelper;
	
	public CheckUpdateTask(QueenStreetApplication queenStreetApplication, BaseActivity baseActivity, Handler handler, MessageHelper messageHelper) {
		super();
		this.queenStreetApplication = queenStreetApplication;
		this.baseActivity = baseActivity;
		this.handler = handler;
		this.messageHelper = messageHelper;
	}

	@Override
	public void run() {
		try {
			RemoteManager remoteManager = RemoteManager.getSecurityRemoteManager();
			String checkUpdateUrl = Config.getConfig().getProperty(Config.Names.CHECK_UPDATE_URL);
			Request request = remoteManager.createQueryRequest(checkUpdateUrl);
			Response response = remoteManager.execute(request);
			if (!response.isSuccess()) {
				Log.w("check update", "fail, because of " + response.getMessage());
				return;
			}
			JSONObject jsonObject = (JSONObject)response.getModel();
			JSONObject json = jsonObject.getJSONObject("data");
			int lastAndroidVersion = JsonUtil.getInt(json, "lastAndroidVersion", 1);
			String lastAndroidClientUrl = JsonUtil.getString(json, "androidUrl", "");
//			int acceptLowestAndroidVersion = JsonUtil.getInt(json, "lastAndroidVersion", 0);
			logInfo("lastAndroidVersion:" + lastAndroidVersion);
			logInfo("lastAndroidClientUrl:" + lastAndroidClientUrl);
//			logInfo("acceptLowestAndroidVersion:" + acceptLowestAndroidVersion);
			
			this.lastAndroidClientUrl = lastAndroidClientUrl;
			
			final int currentVersion = queenStreetApplication.getVersionCode();
			
			if (currentVersion >= lastAndroidVersion) {
				logInfo("current version:" + currentVersion + ", need not update.");
				return;
			}
			
			/*if (currentVersion < acceptLowestAndroidVersion) {
				logInfo("current version:" + currentVersion + ", but server accept version:" + acceptLowestAndroidVersion + ", must be update!");
				// 客户端版本太低，必须升级！！
				// O 这里应该改成强制升级
				alertChooseForUpdate();
				return;
			}*/
			
			if (currentVersion < lastAndroidVersion) {
				logInfo("current version:" + currentVersion + ", lastAndroidVersion:" + lastAndroidVersion + ", you can choose update.");
				// 有新的客户端版本，是否需要更新？
				alertChooseForUpdate();
				return;
			}
			
			
		} catch (Exception e) {
			Log.e("check update", e.getMessage(), e);
		}
		
	}
	
	
	private static void logInfo(String msg) {
		Log.i("check update", msg);
	}
	
	private void alertChooseForUpdate() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				try {
					AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
					AlertDialog alertDialog = builder.create();
					alertDialog.setTitle("软件更新");
					alertDialog.setMessage("有新的客户端版本，是否更新到最新版本？");
					alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "更新", updateClientOnClickListener);
					alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "不更新", doNotUpdateOnClickListener);
					alertDialog.show();
					messageHelper.set(true);
				} catch (Exception e) {
					Log.e("check update", e.getMessage(), e);
				}
			}
		});
	}
	
	OnClickListener updateClientOnClickListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new  Intent();
			intent.putExtra("lastAndroidClientUrl", lastAndroidClientUrl);
			intent.setClass(baseActivity, UpdateClientActivity.class);
			baseActivity.startActivity(intent);
		}
		
	};
	
	OnClickListener doNotUpdateOnClickListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent();
			intent.setClass(baseActivity, NavigationActivity.class);
			baseActivity.startActivity(intent);
			baseActivity.finish();
		}
		
	};
	
	
	
}

