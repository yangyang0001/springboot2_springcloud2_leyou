package com.inspur;

import com.inspur.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class})
public class UserAppTest {


	@Autowired
	private RestTemplate restTemplate;


	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Test
	public void testQueryUserByUserId() {
		User user = restTemplate.getForObject("http://localhost:8888/user/1", User.class);
		System.err.println(user);
	}
}
