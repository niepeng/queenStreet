package com.baibutao.apps.queenstreet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.baibutao.apps.queenstreet.R;
import com.baibutao.apps.queenstreet.activities.common.BaseActivity;
import com.baibutao.apps.queenstreet.tasks.CheckUpdateTask;
import com.baibutao.apps.queenstreet.tasks.MessageHelper;
import com.baibutao.apps.queenstreet.tasks.TaskGroup;

/**
 * @author lsb
 * 
 * @date 2012-5-29 下午10:36:13
 */
public class WelcomeActivity extends BaseActivity {
	
	private Handler handler;
	
	private static final long waitTime = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.welcome);
		
		handler = new Handler();
		final MessageHelper messageHelper = new MessageHelper(false);
		final TaskGroup taskGroup = new TaskGroup();

		Runnable navigationThread = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(waitTime);
					// 如果已经弹出自动更新对话框，然用户选择后自己进去
					if (!messageHelper.set()) {
						startNaviagetionEventWhere();
					}
					
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					logError("", e);
				} finally {
					taskGroup.shutdown();
				}
			}
		};
		
		taskGroup.addMust(navigationThread);
		taskGroup.addMay(new CheckUpdateTask(queenStreetApplication, this, handler, messageHelper));
		
		queenStreetApplication.asyCall(taskGroup);
		
	}
	
	private void startNaviagetionEventWhere() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				startNaviagetion();
			}
		});

	}

	private void startNaviagetion() {
		Intent intent = new Intent();
		intent.setClass(this, NavigationActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	

}
