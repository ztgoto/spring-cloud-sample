package io.github.ztgoto.cloud.consumer.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.github.ztgoto.cloud.consumer.services.TestRibbonService;

@Service
public class TestRibbonServiceImpl implements TestRibbonService {
	
	@Resource
	RestTemplate restTemplate;
	
	String helloUrl="http://cloud-provider/test/hello";
	
	String messageUrl="http://cloud-provider/test/hystrix";

	
	@Override
	public String ribbonHello(String name) {
		
		return restTemplate.getForObject(helloUrl+"?name="+name, String.class);
	}

	@HystrixCommand(
			groupKey="TestHystrix"
			,commandKey="ribbonHystrixMessage"
			,commandProperties={
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500"),
			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50")
			}
			,threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "100"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "10")
	},fallbackMethod="fallback")
	@Override
	public String ribbonHystrixMessage(String message) {
		
		return restTemplate.getForObject(messageUrl+"?message="+message, String.class);
	}
	
	public String fallback(String message){
		return "hystirx fallback:"+message;
	}

}
