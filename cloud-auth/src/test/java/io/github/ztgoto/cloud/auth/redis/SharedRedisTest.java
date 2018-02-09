package io.github.ztgoto.cloud.auth.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

@RunWith(BlockJUnit4ClassRunner.class)
public class SharedRedisTest {

	@Test
	public void testShared() {
		
		JedisPoolConfig config =new JedisPoolConfig();//Jedis池配置


        config.setTestOnBorrow(true);
        
		ShardedRedisConnectionFactory factory = new ShardedRedisConnectionFactory(config);
		JedisShardInfo  jsi1= new JedisShardInfo("redis://192.168.4.117:6379/10");
		JedisShardInfo  jsi2= new JedisShardInfo("redis://192.168.4.117:6379/11");
		JedisShardInfo  jsi3= new JedisShardInfo("redis://192.168.4.117:6379/12");
		factory.addShardInfo(jsi1);
		factory.addShardInfo(jsi2);
		factory.addShardInfo(jsi3);
		factory.init();
		
		RedisConnection conn = factory.getConnection();
		
		for (int i = 0; i < 10; i++) {
			String key = "key"+i;
			String value = "value"+i;
			conn.set(key, value);
			conn.expire(key, 30);
			
			System.out.println(conn.get(key));
		}
		
		conn.close();
		
		factory.destroy();
	}
	
	

}
