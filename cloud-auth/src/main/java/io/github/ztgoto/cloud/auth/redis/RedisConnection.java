package io.github.ztgoto.cloud.auth.redis;

public interface RedisConnection extends RedisCommands,BinaryRedisCommands {
	
	void close();
	
	boolean isClosed();
	
}
