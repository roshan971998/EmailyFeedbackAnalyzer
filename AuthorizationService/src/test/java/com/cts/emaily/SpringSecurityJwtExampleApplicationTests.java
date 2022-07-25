package com.cts.emaily;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

/**
 * Test - AuthServiceApplication
 */
@SpringBootTest
class SpringSecurityJwtExampleApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void appplicationStarts() throws IOException {
		SpringSecurityJwtExampleApplication.main(new String[] {});
		assertTrue(true);
	}
}
