package gov.nyc.doitt.ccrb.statuslookup.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(locations = {"classpath:/testApplicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BaseTest {
	@Value("${ccrbsl.agencysite}")
	private String agencysite;
	
	@Test
	public void testApplicationContext() throws Exception {
		assertNotNull(agencysite);
	}
}
