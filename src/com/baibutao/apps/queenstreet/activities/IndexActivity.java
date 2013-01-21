package com.baibutao.apps.queenstreet.activities;

import android.os.Bundle;
import android.view.View;

import com.baibutao.apps.queenstreet.R;
import com.baibutao.apps.queenstreet.activities.common.BaseActivity;

/**
 * @author lsb
 *
 * @date 2012-5-29 下午10:52:49
 */
public class IndexActivity  extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.index);
	}	
	
	
	
	public void handleReflush(View v) {
		alert("点击了刷新");
	}
}
