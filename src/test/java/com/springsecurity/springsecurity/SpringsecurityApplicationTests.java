package com.springsecurity.springsecurity;

import static org.assertj.core.api.Assertions.assertThat;
import com.springsecurity.springsecurity.controllers.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org. springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SpringsecurityApplicationTests {

	@Autowired
	private LoginController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
