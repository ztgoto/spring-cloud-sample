package io.github.ztgoto.cloud.auth.configure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import redis.clients.jedis.Protocol;

@ConfigurationProperties(prefix = "spring.redis")
public class ShardedRedisProperties {
	
	private List<ShardingInfo> sharding = new ArrayList<ShardingInfo>();
	
	public List<ShardingInfo> getSharding() {
		return sharding;
	}

	public static class ShardingInfo {
		private String host;
		private String passowrd;
		private int timeout = Protocol.DEFAULT_TIMEOUT;
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public String getPassowrd() {
			return passowrd;
		}
		public void setPassowrd(String passowrd) {
			this.passowrd = passowrd;
		}
		public int getTimeout() {
			return timeout;
		}
		public void setTimeout(int timeout) {
			this.timeout = timeout;
		}
		
		
	}
	
	

}
