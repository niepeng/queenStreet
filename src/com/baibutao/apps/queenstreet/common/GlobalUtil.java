package com.baibutao.apps.queenstreet.common;

import com.baibutao.apps.queenstreet.androidext.QueenStreetApplication;

/**
 * @author lsb
 *
 * @date 2012-5-30 上午11:16:29
 */
public class GlobalUtil {
	
	private static QueenStreetApplication queenStreetApplication;

	private static float density = 1.0f;
	
	public static QueenStreetApplication getChildayApplication() {
		return queenStreetApplication;
	}
	
	public static void init(QueenStreetApplication childayApplication) {
		GlobalUtil.queenStreetApplication = childayApplication;
		density = childayApplication.getResources().getDisplayMetrics().density;
	}
	
	public static int pixelToDip(int pixel) {
		return (int)(pixel / density);
	}
	
	public static int dipToPixel(int dip) {
		return (int)(dip * density);
	}
	
}
