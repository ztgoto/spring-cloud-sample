package io.github.ztgoto.cloud.auth.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

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
		
		ShardedRedisTemplate<Object, Object> oper = new ShardedRedisTemplate<Object, Object>();
		oper.setKeySerializer(new StringRedisSerializer());
		oper.setValueSerializer(new FastJsonRedisSerializer<>(TestInfo.class));
		oper.setRedisConnectionFactory(factory);
		
		
		for (int i = 0; i < 10; i++) {
			String key = "key"+i;
			TestInfo value = new TestInfo("name"+i, i);
			oper.set(key, value, 30000, TimeUnit.MILLISECONDS);
			
			
			System.out.println(oper.get(key));
		}
		
		
		factory.destroy();
	}
	
	public static class TestInfo implements Serializable {
		
		public TestInfo(String name, Integer age) {
			this.name = name;
			this.age = age;
		}
		private String name;
		private Integer age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		
	}
	

}
