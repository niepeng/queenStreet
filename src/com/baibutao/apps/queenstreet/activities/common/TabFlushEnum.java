package com.baibutao.apps.queenstreet.activities.common;

import com.baibutao.apps.queenstreet.activities.CategoryActivity;
import com.baibutao.apps.queenstreet.activities.MoreActivity;
import com.baibutao.apps.queenstreet.activities.IndexActivity;
import com.baibutao.apps.queenstreet.activities.UserCenterActivity;

/**
 * @author lsb
 *
 * @date 2012-5-29 下午10:51:21
 */
public enum TabFlushEnum {
	NEWS_LIST(0, IndexActivity.class),
	HOT_THREAD_LIST(1,CategoryActivity.class),
	USER_CENTER(2, UserCenterActivity.class),
	MORE(3, MoreActivity.class);
	
	private int index;
	
	private Class<?> clazz;
	
	private boolean isFlush;
	
	private TabFlushEnum(int index, Class<?> claz) {
		this.index = index;
		this.clazz = claz;
		this.isFlush = false;
	}
	
	public int getIndex() {
		return index;
	}
	
	public TabFlushEnum getEnum(int index) {
		return index < (TabFlushEnum.values().length - 1) ? TabFlushEnum.values()[index] : null;
	}
	
	public static int getEnumIndex(Class<?> cla) {
		int index = -1;
		for(TabFlushEnum temp : TabFlushEnum.values()) {
			if(temp.getTabActivity() == cla) {
				index = temp.getIndex();
				break;
			}
		}
		return index;
	}
	
	public static String[] getClassName() {
		String[] classNames = new String[TabFlushEnum.values().length];
		for(int i= 0; i< TabFlushEnum.values().length; i++) {
			classNames[i] = TabFlushEnum.values().getClass().getName();
		}
		return classNames;
	}
	
	
	public Class<?> getTabActivity() {
		return clazz;
	}
	
	public boolean isFlush() {
		return isFlush;
	}
	
	public void setFlush(boolean flag) {
		isFlush = flag;
	}
}

