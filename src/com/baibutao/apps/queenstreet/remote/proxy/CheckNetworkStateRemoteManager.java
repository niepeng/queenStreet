package com.baibutao.apps.queenstreet.remote.proxy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.baibutao.apps.queenstreet.common.MessageCodes;
import com.baibutao.apps.queenstreet.remote.RemoteManager;
import com.baibutao.apps.queenstreet.remote.Request;
import com.baibutao.apps.queenstreet.remote.Response;

/**
 * @author lsb
 *
 * @date 2012-5-29 下午11:39:22
 */
public class CheckNetworkStateRemoteManager extends RemoteManager {

	private RemoteManager remoteManager;

	public CheckNetworkStateRemoteManager(RemoteManager remoteManager) {
		super();
		this.remoteManager = remoteManager;
	}

	@Override
	public Request createPostRequest(String target) {
		return remoteManager.createPostRequest(target);
	}

	@Override
	public Request createQueryRequest(String target) {
		return remoteManager.createQueryRequest(target);
	}

	@Override
	public Response execute(Request request) {
		if (!hasNetWork()) {
			return new Response(MessageCodes.HAS_NOT_NETWORK, "没有可用的网络连接。");
		}
		return remoteManager.execute(request);
	}

	protected boolean hasNetWork() {
		ConnectivityManager nw = (ConnectivityManager) queenStreetApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (nw == null) {
			return false;
		}
		NetworkInfo netinfo = nw.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		return netinfo.isAvailable();
	}

}

