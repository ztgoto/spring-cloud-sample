package io.github.ztgoto.cloud.auth.configure;

import java.util.List;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class ShardingJedisConnectionFactory extends JedisConnectionFactory {
	
	private List<JedisShardInfo> shardInfos;
	
	private ShardedJedisPool shardedJedisPool;
	
	

	@Override
	public void afterPropertiesSet() {
		
		if (getUsePool() && shardInfos!=null && shardInfos.size()>0) {
			createShardedJedisPool();
			return;
		}
		
		super.afterPropertiesSet();
	}
	
	
	
	@Override
	public RedisConnection getConnection() {
		if (getUsePool() && shardedJedisPool != null) {
			return getShardingConnection();
		}
		return super.getConnection();
	}
	
	protected RedisConnection getShardingConnection() {
		
		ShardedJedis jedis = shardedJedisPool.getResource();
		
		
		
		return null;
	}



	protected void createShardedJedisPool(){
		this.shardedJedisPool = new ShardedJedisPool(getPoolConfig(), shardInfos
				, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
	}

	public List<JedisShardInfo> getShardInfos() {
		return shardInfos;
	}

	public void setShardInfos(List<JedisShardInfo> shardInfos) {
		this.shardInfos = shardInfos;
	}
}
