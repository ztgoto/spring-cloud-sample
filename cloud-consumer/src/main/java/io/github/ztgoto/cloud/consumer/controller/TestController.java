package io.github.ztgoto.cloud.consumer.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.ztgoto.cloud.consumer.services.TestFeignService;
import io.github.ztgoto.cloud.consumer.services.TestRibbonService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Resource
	TestRibbonService testRibbonService;
	
	@Resource
	TestFeignService testFeignService;
	
	@RequestMapping("/ribbonHello")
	public String ribbonHello(@RequestParam("name") String name){
		return testRibbonService.ribbonHello(name);
	}
	
	@RequestMapping("/feignHello")
	public String feignHello(@RequestParam("name") String name){
		return testFeignService.feignHello(name);
	}
	
	@RequestMapping("/ribbonHystrixMessage")
	public String ribbonHystrixMessage(@RequestParam(value="message",required=false) String message){
		return testRibbonService.ribbonHystrixMessage(message);
	}
	
	@RequestMapping("/feignHystrixMessage")
	public String feignHystrixMessage(@RequestParam(value="message",required=false) String message){
		return testFeignService.feignHystrixMessage(message);
	}
	
	

}
