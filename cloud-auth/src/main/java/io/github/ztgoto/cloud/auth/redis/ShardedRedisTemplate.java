package io.github.ztgoto.cloud.auth.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.serializer.RedisSerializer;

@SuppressWarnings({"rawtypes", "unused"})
public class ShardedRedisTemplate<K, V> implements SharedRedisOperations<K, V> {
	
	private RedisSerializer keySerializer = null;
	private RedisSerializer valueSerializer = null;
	
	private ShardedRedisConnectionFactory  redisConnectionFactory;

	@Override
	public void set(K key, V value) {
		RedisConnection connection = null;
		
		try {
			connection = redisConnectionFactory.getConnection();
			byte[] bkey = keySerializer.serialize(key);
			byte[] bvalue = valueSerializer.serialize(value); 
			connection.set(bkey, bvalue);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		
		
	}

	@Override
	public void set(K key, V value, long timeout, TimeUnit unit) {
		
		RedisConnection connection = null;
		
		try {
			connection = redisConnectionFactory.getConnection();
			byte[] bkey = keySerializer.serialize(key);
			byte[] bvalue = valueSerializer.serialize(value); 
			
			connection.setex(bkey, (int)unit.toSeconds(timeout), bvalue);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		
	}
	
	@Override
	public V get(Object key) {
		RedisConnection connection = null;
		
		try {
			connection = redisConnectionFactory.getConnection();
			byte[] bkey = keySerializer.serialize(key);
			
			byte[] bdata = connection.get(bkey);
			return (V) valueSerializer.deserialize(bdata);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void setKeySerializer(RedisSerializer keySerializer) {
		this.keySerializer = keySerializer;
	}

	public void setValueSerializer(RedisSerializer valueSerializer) {
		this.valueSerializer = valueSerializer;
	}

	public void setRedisConnectionFactory(ShardedRedisConnectionFactory redisConnectionFactory) {
		this.redisConnectionFactory = redisConnectionFactory;
	}


}
