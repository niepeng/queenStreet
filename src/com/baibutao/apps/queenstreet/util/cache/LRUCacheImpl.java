package com.baibutao.apps.queenstreet.util.cache;
import java.util.LinkedHashMap;

/**
 * @author lsb
 *
 * @date 2012-5-29 下午11:50:10
 */
public class LRUCacheImpl<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = -3274574530992748967L;
	
	private int maxSize = 200;
	
	public LRUCacheImpl() {
		super();
	}
	
	public LRUCacheImpl(int maxSize) {
		super();
		this.maxSize = maxSize;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return this.size() > maxSize;
	}

}

