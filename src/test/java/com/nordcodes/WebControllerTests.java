package com.nordcodes;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nordcodes.config.ViewResolverConfig;
import com.nordcodes.controller.WebController;
import com.nordcodes.repositories.UserRepository;

@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ViewResolverConfig.class)
@WebAppConfiguration

public class WebControllerTests {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeAll
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	
	@Test
	public void getIndexPageTest() throws Exception{
		/*	mvc.perform(MockMvcRequestBuilders
	      .get("/")
	      .accept(MediaType.APPLICATION_JSON))
	      //.andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.shortURL").exists())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.shortURL.longURL").isEmpty());*/
	}
}
