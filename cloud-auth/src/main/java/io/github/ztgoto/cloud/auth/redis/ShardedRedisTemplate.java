package io.github.ztgoto.cloud.auth.redis;

import java.util.concurrent.TimeUnit;

public class ShardedRedisTemplate<K, V> implements SharedRedisOperations<K, V> {

	@Override
	public void set(K key, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(K key, V value, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}


}
