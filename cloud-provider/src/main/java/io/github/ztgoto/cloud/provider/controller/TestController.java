package io.github.ztgoto.cloud.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Value("${server.port}")
	Integer port;
	
	@RequestMapping("/hello")
	public String hello(@RequestParam("name") String name){
		return "hello:"+name+":"+port;
	}
	
	@RequestMapping("/hystrix")
	public String hystrix(@RequestParam(value="message",required=false) String message) throws InterruptedException{
		Thread.sleep(2000);
		return "hystirx:"+message+":"+port;
	}

}
