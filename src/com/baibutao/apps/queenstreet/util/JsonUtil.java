package com.baibutao.apps.queenstreet.util;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lsb
 * 
 * @date 2012-5-30 上午10:31:25
 */
public class JsonUtil {
	public static JSONObject getJSONObject(JSONObject json, String name) {
		if (json == null || name == null) {
			return null;
		}
		if (!json.has(name)) {
			return null;
		}
		try {
			return json.getJSONObject(name);
		} catch (JSONException e) {
			return null;
		}
	}

	public static String getString(JSONObject json, String name, String defaultValue) {
		if (json == null || name == null) {
			return defaultValue;
		}
		if (!json.has(name)) {
			return defaultValue;
		}
		try {
			return json.getString(name);
		} catch (JSONException e) {
			return defaultValue;
		}
	}

	public static int getInt(JSONObject json, String name, int defaultValue) {
		if (json == null || name == null) {
			return defaultValue;
		}
		if (!json.has(name)) {
			return defaultValue;
		}
		try {
			return json.getInt(name);
		} catch (JSONException e) {
			return defaultValue;
		}
	}

	public static long getLong(JSONObject json, String name, long defaultValue) {
		if (json == null || name == null) {
			return defaultValue;
		}
		if (!json.has(name)) {
			return defaultValue;
		}
		try {
			return json.getLong(name);
		} catch (JSONException e) {
			return defaultValue;
		}
	}

	public static double getDouble(JSONObject json, String name, double defaultValue) {
		if (json == null || name == null) {
			return defaultValue;
		}
		if (!json.has(name)) {
			return defaultValue;
		}
		try {
			return json.getDouble(name);
		} catch (JSONException e) {
			return defaultValue;
		}
	}
}
