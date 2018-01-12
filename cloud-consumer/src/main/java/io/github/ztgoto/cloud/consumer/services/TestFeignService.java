package io.github.ztgoto.cloud.consumer.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.ztgoto.cloud.consumer.services.impl.TestFeignServiceHystrixFallback;

@FeignClient(value="cloud-provider",fallback=TestFeignServiceHystrixFallback.class)
public interface TestFeignService {
	
	@RequestMapping(value="/test/hello")
	String feignHello(@RequestParam("name") String name);
	
	@RequestMapping(value="/test/hystrix")
	String feignHystrixMessage(@RequestParam(value="message",required=false) String message);
}
