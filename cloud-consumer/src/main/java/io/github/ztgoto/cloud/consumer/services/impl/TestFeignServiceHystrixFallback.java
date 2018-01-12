package io.github.ztgoto.cloud.consumer.services.impl;

import org.springframework.stereotype.Service;

import io.github.ztgoto.cloud.consumer.services.TestFeignService;

@Service
public class TestFeignServiceHystrixFallback implements TestFeignService {

	@Override
	public String feignHello(String name) {
		
		return "hystrix fallback feignHello:"+name;
	}

	@Override
	public String feignHystrixMessage(String message) {
		
		return "hystrix fallback feignHystirxMessage:"+message;
	}

}
