package com.baibutao.apps.queenstreet.activities;

import android.os.Bundle;
import android.view.View;

import com.baibutao.apps.queenstreet.R;
import com.baibutao.apps.queenstreet.activities.common.BaseActivity;

/**
 * @author lsb
 *
 * @date 2012-5-29 下午10:53:17
 */
public class UserCenterActivity  extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.user_center);
	}	
	
	
	public void handlePublish(View v) {
		alert("点击了发表");
	}
}
