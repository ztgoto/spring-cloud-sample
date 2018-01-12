package io.github.ztgoto.cloud.zuul;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.util.StringUtils;

@EnableZuulProxy
@SpringBootApplication
public class Run {

	static final Logger LOGGER = LoggerFactory.getLogger(Run.class);

	// Spring profile
	public static final String SPRING_PROFILE_LOCALHOST = "local";
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
	public static final String SPRING_PROFILE_TEST = "test";
	public static final String SPRING_PROFILE_PRODUCTION = "prod";
	public static final String SPRING_PROFILE_FAST = "fast";

	static {
		System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
	}

	public static void main(String[] args) throws UnknownHostException {
		long begin = System.currentTimeMillis();
		
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

		SpringApplication app = new SpringApplication(Run.class);
		
		// 采用命令启动时如没有设置启动环境，则设置默认启动环境
		if (!source.containsProperty("spring.profiles.active") && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")
				&& StringUtils.isEmpty(System.getProperty("spring.profiles.active"))) {
			app.setAdditionalProfiles(SPRING_PROFILE_LOCALHOST);
		}
		
		ConfigurableApplicationContext context = app.run(args);
		
		Environment env = context.getEnvironment();
		
		String runInfo = ManagementFactory.getRuntimeMXBean().getName();
		
		long end = System.currentTimeMillis();
		
		LOGGER.info(
				"Access URLs:\n----------------------------------------------------------\n\t"
						+ "Local: \t\thttp://127.0.0.1:{}\n\t" + "External: \thttp://{}:{}\n\t"
						+ "Pid: \t\t{}\n\t"
						+ "Profiles: \t{}\n\t"
						+ "StartTime: \t{}\n----------------------------------------------------------",
						env.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
						env.getProperty("server.port"),
						runInfo.split("@")[0],
						org.springframework.util.StringUtils.arrayToCommaDelimitedString(env.getActiveProfiles())
						,(end-begin));

	}

}
