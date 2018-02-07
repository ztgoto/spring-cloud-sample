package io.github.ztgoto.cloud.auth.crypto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(BlockJUnit4ClassRunner.class)
public class CryptoTest {
	
	@Test
	public void testPasswordEncoder(){
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder(10);
		String password = "123456";
		String encodePwd = pe.encode(password);
		System.out.println(encodePwd);
		System.out.println(pe.matches(password, encodePwd));
		System.out.println(pe.matches("654321", encodePwd));
	}

}
