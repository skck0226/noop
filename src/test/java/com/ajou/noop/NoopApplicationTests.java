package com.ajou.noop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class NoopApplicationTests {
	@Autowired private ApplicationContext applicationContext;

	@Test
	public void contextLoads() throws Exception {
		if (applicationContext != null) {
			String[] beans = applicationContext.getBeanDefinitionNames();

			for (String bean : beans) {
				System.out.println("bean : " + bean);
			}
		}
	}

}
