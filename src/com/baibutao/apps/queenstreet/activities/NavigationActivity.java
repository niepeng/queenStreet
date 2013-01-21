package com.baibutao.apps.queenstreet.activities;

import java.lang.reflect.Field;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.baibutao.apps.queenstreet.R;
import com.baibutao.apps.queenstreet.activities.common.BaseActivity;
import com.baibutao.apps.queenstreet.activities.common.NavigationTabIndexEnum;
import com.baibutao.apps.queenstreet.androidext.QueenStreetApplication;
import com.baibutao.apps.queenstreet.common.GlobalUtil;

/**
 * @author lsb
 * 
 * @date 2012-5-30 上午10:29:13
 */
public class NavigationActivity extends BaseActivity {
	
	// 默认选中tab的index
	private final int DEFAULT_CHECKED = 0;

	private TabHost tabs;

	private static final int NAVEIGATION[][] = {
	
	{ R.drawable.foot_news_1, R.drawable.foot_news_1 }, 
	{ R.drawable.foot_hot_thread_1, R.drawable.foot_hot_thread_1 },
	{ R.drawable.foot_user_center_1, R.drawable.foot_user_center_1 }, 
	{ R.drawable.foot_more_1, R.drawable.foot_more_1 } };

	protected static final Class<?>[] TAB_ITEMS = { IndexActivity.class, CategoryActivity.class, UserCenterActivity.class, MoreActivity.class };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.navigation);
		tabs = (TabHost) findViewById(android.R.id.tabhost);
		QueenStreetApplication app = (QueenStreetApplication) getApplication();
		app.setTabHost(tabs);

		final TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		int width = GlobalUtil.dipToPixel(50);
		int height = GlobalUtil.dipToPixel(70);

		tabs.setup(this.getLocalActivityManager());

		addIndex(tabs);
		addCategory(tabs);
		addUserCenter(tabs);
		addMore(tabs);

		tabs.setCurrentTab(DEFAULT_CHECKED);

		tabWidget.setPadding(0, 0, 0, 0);

		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			RelativeLayout relativeLayout = (RelativeLayout) tabWidget.getChildAt(i);
			relativeLayout.getLayoutParams().height = height;
			relativeLayout.getLayoutParams().width = width;
			LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams)relativeLayout.getLayoutParams();
			layout.setMargins(10, 4, 10, 2);
			relativeLayout.setPadding(0, -3, 0, 0);

			final TextView tv = (TextView) relativeLayout.findViewById(android.R.id.title);
			tv.setPadding(tv.getPaddingLeft(), -2, tv.getPaddingRight(), 5);
			tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
			tv.setTextSize(11);
			tv.setGravity(Gravity.CENTER);

			if (i == DEFAULT_CHECKED) {
				tv.setCompoundDrawablesWithIntrinsicBounds(0, NAVEIGATION[i][1], 0, 0);
				// relativeLayout.setBackgroundResource(R.drawable.detail_btn_bg_selected);
				// relativeLayout.setBackgroundResource(R.drawable.detail_btn_selector);
				relativeLayout.setBackgroundResource(R.color.blue_deep);
			} else {
				tv.setCompoundDrawablesWithIntrinsicBounds(0, NAVEIGATION[i][0], 0, 0);
				// relativeLayout.setBackgroundResource(R.drawable.tab_one_normal);
				relativeLayout.setBackgroundResource(R.drawable.detail_btn_selector);
			}
		}

		
		Field mBottomLeftStrip;
		Field mBottomRightStrip;
		if (Integer.parseInt(Build.VERSION.SDK) <= 7) {
			try {
				mBottomLeftStrip = tabWidget.getClass().getDeclaredField("mBottomLeftStrip");
				mBottomRightStrip = tabWidget.getClass().getDeclaredField("mBottomRightStrip");
				if (!mBottomLeftStrip.isAccessible()) {
					mBottomLeftStrip.setAccessible(true);
				}
				if (!mBottomRightStrip.isAccessible()) {
					mBottomRightStrip.setAccessible(true);
				}
				mBottomLeftStrip.set(tabWidget, getResources().getDrawable(R.drawable.tab_one_normal));
				mBottomRightStrip.set(tabWidget, getResources().getDrawable(R.drawable.tab_one_normal));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			tabWidget.setStripEnabled(false);
		}

		/**
		 * 当点击tab选项卡的时候，更换图片
		 */
		tabs.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < tabWidget.getChildCount(); i++) {
					RelativeLayout relativeLayout = (RelativeLayout) tabWidget.getChildAt(i);
					final TextView tv = (TextView) relativeLayout.findViewById(android.R.id.title);
					if (tabs.getCurrentTab() == i) {
						tv.setCompoundDrawablesWithIntrinsicBounds(0, NAVEIGATION[i][1], 0, 0);
						// relativeLayout.setBackgroundResource(R.drawable.detail_btn_bg_selected);
						relativeLayout.setBackgroundResource(R.color.blue_deep);
						/*if (i < childayApplication.tabFlushEnums.length) {
							TabFlushEnum tabFlushEnum = childayApplication.tabFlushEnums[i];
							if (tabFlushEnum != null && tabFlushEnum.isFlush()) {
								tabFlushEnum.setFlush(false);
								reflushActivity(i);
							}
						}*/
					} else {
						tv.setCompoundDrawablesWithIntrinsicBounds(0, NAVEIGATION[i][0], 0, 0);
						// relativeLayout.setBackgroundResource(R.drawable.tab_one_normal);
						relativeLayout.setBackgroundResource(R.drawable.detail_btn_selector);
					}
				}
			}

			/*private void reflushActivity(int index) {
				BaseActivity baseActivity = getActivity(TAB_ITEMS[index]);
				if (baseActivity == null) {
					return;
				}
				baseActivity.init();
			}

			private BaseActivity getActivity(Class<?> cls) {
				return (BaseActivity) linkshopApplication.getRunActivityByName(cls.getName());
			}*/

		});
	}

	private void addIndex(TabHost tabs) {
		Intent intent = new Intent(NavigationActivity.this, IndexActivity.class);
		TabSpec tabSpec = tabs.newTabSpec(NavigationTabIndexEnum.INDEX.getStringValue());
		tabSpec.setIndicator("首页", null);
		tabSpec.setContent(intent);

		tabs.addTab(tabSpec);
	}

	private void addCategory(TabHost tabs) {
		Intent intent = new Intent(NavigationActivity.this, CategoryActivity.class);
		TabSpec tabSpec = tabs.newTabSpec(NavigationTabIndexEnum.CATEGORY.getStringValue());
		tabSpec.setIndicator("分类", null);
		tabSpec.setContent(intent);

		tabs.addTab(tabSpec);
	}

	private void addUserCenter(TabHost tabs) {
		Intent intent = new Intent(NavigationActivity.this, UserCenterActivity.class);
		TabSpec tabSpec = tabs.newTabSpec(NavigationTabIndexEnum.USER_CENTER.getStringValue());
		tabSpec.setIndicator("我喜欢", null);
		tabSpec.setContent(intent);

		tabs.addTab(tabSpec);
	}

	private void addMore(TabHost tabs) {
		Intent intent = new Intent(NavigationActivity.this, MoreActivity.class);
		TabSpec tabSpec = tabs.newTabSpec(NavigationTabIndexEnum.MORE.getStringValue());
		tabSpec.setIndicator("设置", null);
		tabSpec.setContent(intent);

		tabs.addTab(tabSpec);
	}

}