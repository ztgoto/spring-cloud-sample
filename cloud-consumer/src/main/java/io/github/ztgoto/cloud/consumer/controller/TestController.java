package io.github.ztgoto.cloud.consumer.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.github.ztgoto.cloud.consumer.grpc.Client;
import io.github.ztgoto.cloud.consumer.services.TestFeignService;
import io.github.ztgoto.cloud.consumer.services.TestRibbonService;
import io.github.ztgoto.grpc.messages.RequestMessage;

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
	
	Map<String, Client> clients = Maps.newConcurrentMap();
	
	@RequestMapping("/grpcCheck")
	public boolean grpcCheck(@RequestParam("host") String host, @RequestParam("port") Integer port,HttpServletRequest request){
		String key = host.trim()+":"+port;
		Client c = clients.get(key);
		if (c == null) {
			c = new Client(host, port);
			clients.put(key, c);
		}
		try {
			RequestMessage.Builder build = RequestMessage
					.newBuilder()
					.setUri(request.getRequestURL().toString())
					.setContentType("application/json")
					.setMethod("PUT")
					.setToken("abc123456");
			boolean result = c.check(build.build());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				c.shutdown();
				clients.remove(key);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	

}
