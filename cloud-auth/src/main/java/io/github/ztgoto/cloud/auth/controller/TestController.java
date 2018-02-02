package io.github.ztgoto.cloud.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/public")
	public String message1(@RequestParam("message") String message){
		
		return "message:"+message;
	}
	
	@RequestMapping("/private")
	public String message2(@RequestParam("message") String message){
		
		return "message:"+message;
	}
	
}
