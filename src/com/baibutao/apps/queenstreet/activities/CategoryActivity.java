package com.baibutao.apps.queenstreet.activities;
import android.os.Bundle;
import android.view.View;

import com.baibutao.apps.queenstreet.R;
import com.baibutao.apps.queenstreet.activities.common.BaseActivity;

/**
 * @author lsb
 *
 * @date 2012-5-29 下午10:52:57
 */
public class CategoryActivity  extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.category);
	}	
	
	public void handlePublish(View v) {
		alert("点击了发帖");
	}
}
