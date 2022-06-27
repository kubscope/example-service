package com.ks.example;

import com.ks.example.controller.UserServiceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class ExampleServiceApplicationTests {

	@Autowired
	private UserServiceController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void main() {
		ExampleServiceApplication.main(new String[] {});
	}
//	@Test
//	void contextLoads() {
//	}

}
