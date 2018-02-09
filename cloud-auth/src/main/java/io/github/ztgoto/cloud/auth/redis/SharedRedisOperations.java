package io.github.ztgoto.cloud.auth.redis;

import java.util.concurrent.TimeUnit;

public interface SharedRedisOperations <K, V> {
	
	void set(K key, V value);
	
	void set(K key, V value, long timeout, TimeUnit unit);
	
	V get(Object key);

}
