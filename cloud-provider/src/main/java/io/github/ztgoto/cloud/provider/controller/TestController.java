package io.github.ztgoto.cloud.provider.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.DiscoveryClient;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Value("${server.port}")
	Integer port;
	
	@Resource
	Environment environment;
	
	@Resource
	EurekaDiscoveryClient discoveryClient;
	
	@RequestMapping("/hello")
	public String hello(@RequestParam("name") String name){
		return "hello:"+name+":"+port;
	}
	
	@RequestMapping("/hystrix")
	public String hystrix(@RequestParam(value="message",required=false) String message) throws InterruptedException{
		Thread.sleep(2000);
		return "hystirx:"+message+":"+port;
	}
	
	@RequestMapping("/config")
	public String config(@RequestParam("key") String key){
		return environment.getProperty(key);
	}
	
	
	@RequestMapping("/serviceInfo")
	public Object serviceInfo(@RequestParam("serviceId") String serviceId){
		return discoveryClient.getInstances(serviceId);
	}
	

}
