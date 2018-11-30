package gov.nyc.doitt.statuslookup.service;

import static org.junit.Assert.*;
import gov.nyc.doitt.ccrb.statuslookup.service.DoITTCaptchaService;
import gov.nyc.doitt.ccrb.statuslookup.service.DoITTCaptchaServiceImpl;
import gov.nyc.doitt.ccrb.statuslookup.test.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DoITTCaptchaServiceTests extends BaseTest {

	@Autowired
	private DoITTCaptchaService doittCaptcha;
	
	//Test property file should have disabled captcha == true
	//Second method actually makes connection to captcha service and should fail

	@Test
	public void testDisabledDoITTCaptchaService() throws Exception {
		String captchaResponse =  doittCaptcha.validateCaptcha("Answer", false);
		System.out.println(captchaResponse);
		//assertNotNull(captchaResponse);
		assertEquals("true", captchaResponse);
	}

	// failure test
	@Test
	public void testFailOnHttpDoITTCaptchaService() throws Exception {
		String captchaResponse =  doittCaptcha.validateCaptcha("Answer", true);
		System.out.println(captchaResponse);
		assertFalse("true".equals(captchaResponse));
	}

}
