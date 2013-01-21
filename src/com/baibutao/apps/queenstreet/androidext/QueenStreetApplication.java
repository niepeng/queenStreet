package com.baibutao.apps.queenstreet.androidext;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import android.app.Activity;
import android.app.Application;
import android.widget.TabHost;

import com.baibutao.apps.queenstreet.activities.common.TabFlushEnum;
import com.baibutao.apps.queenstreet.as.AsynchronizedInvoke;
import com.baibutao.apps.queenstreet.biz.LoadImgDO;
import com.baibutao.apps.queenstreet.config.Config;
import com.baibutao.apps.queenstreet.localcache.ImageCache;
import com.baibutao.apps.queenstreet.remote.RemoteManager;
import com.baibutao.apps.queenstreet.util.CollectionUtil;
import com.baibutao.apps.queenstreet.util.StringUtil;

/**
 * @author lsb
 *
 * @date 2012-5-29 下午10:37:57
 */
public class QueenStreetApplication extends Application {
	
	private AsynchronizedInvoke asynchronizedInvoke;
	
	private TabHost tabHost;
	
//	private UserDO userDO;
	
	public TabFlushEnum[] tabFlushEnums = {TabFlushEnum.NEWS_LIST, TabFlushEnum.HOT_THREAD_LIST, TabFlushEnum.USER_CENTER, TabFlushEnum.MORE};
	
	public static String[] notFinishActivity = TabFlushEnum.getClassName();
	
	private List<Activity> activities = CollectionUtil.newArrayList();

	private int versionCode;
	
	private String versionName;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		
//		userDO = new UserDO();
		
		Config config = Config.getConfig();
    	config.init(this);

//		GlobalUtil.init(this);
    	
    	RemoteManager.init(this);
    	ImageCache.init(this);
    	
    	asynchronizedInvoke = new AsynchronizedInvoke();
		asynchronizedInvoke.init();
		
	}
	
	@Override
	public void onTerminate() {
		asynchronizedInvoke.cleanup();
		super.onTerminate();
	}
	
	
	public void addLoadImage(LoadImgDO loadImgDO) {
		 asynchronizedInvoke.addLoadImage(loadImgDO);
	}
	
	public <V> Future<V> asyInvoke(Callable<V> callable) { 
		return asynchronizedInvoke.invoke(callable);
	}

	public void asyCall(Runnable runnable) {
		asynchronizedInvoke.call(runnable);
	}
	
	public int getVersionCode() {
		if(versionCode == 0) {
			getVersionInfo();
		}
		return versionCode;
	}
	
	private void getVersionInfo() {
		 versionName = "android_" + Config.getConfig().getVersionPerperty("youthday.version.name");
		 versionCode = Integer.parseInt(Config.getConfig().getVersionPerperty("youthday.version.code"));
	}

	public String getVersionName() {
		if(StringUtil.isEmpty(versionName)) {
			getVersionInfo();
		}
		return versionName;
	}
	
	
	
	public void finishAllActivities() {
		synchronized(activities) {
			for (Activity a : activities) {
				a.finish();
			}
			activities.clear();
		}
	}
	
	public void addActivity(Activity activity) {
		if (activity == null) {
			return;
		}
		synchronized(activities) {
			activities.add(activity);
			/*String name = activity.getClass().getName();
			Activity inMap = activities.get(name);
			if (inMap != null && isNeedFinish(name)) {
				inMap.finish();
			} 
			activities.put(name, activity);*/
		}
	}
	
	/*private boolean isNeedFinish(String name) {
		for (String s : notFinishActivity) {
			if (s.equals(name)) {
				return false;
			}
		}
		return true;
	}
	*/
	

//	public UserDO getUserDO() {
//		return userDO;
//	}
	
	public TabHost getTabHost() {
		return tabHost;
	}

	public void setTabHost(TabHost tabHost) {
		this.tabHost = tabHost;
	}
	
}
