package io.github.ztgoto.cloud.consumer.services;

public interface TestRibbonService {
	
	String ribbonHello(String name);
	
	String ribbonHystrixMessage(String message);
	
}
