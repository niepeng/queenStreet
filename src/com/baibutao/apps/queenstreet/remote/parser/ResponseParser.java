package com.baibutao.apps.queenstreet.remote.parser;
import com.baibutao.apps.queenstreet.remote.Response;

/**
 * @author lsb
 *
 * @date 2012-5-29 下午11:30:02
 */
public interface ResponseParser {

	Response parse(byte[] content, String charset, String sessionId);
	
}
