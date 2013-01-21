package com.baibutao.apps.queenstreet.activities.common;
/**
 * @author lsb
 *
 * @date 2012-5-30 上午11:21:02
 */
public enum NavigationTabIndexEnum {
	
	INDEX(0),
	CATEGORY(1),
	USER_CENTER(2),
	MORE(3);
	
	private int index;
	
	private NavigationTabIndexEnum(int index) {
		this.index = index;
	}

	public int getValue() {
		return index;
	}
	
	public String getStringValue() {
		return String.valueOf(index);
	}
}

