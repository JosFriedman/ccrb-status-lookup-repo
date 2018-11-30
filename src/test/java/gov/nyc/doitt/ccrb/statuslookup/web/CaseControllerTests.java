package gov.nyc.doitt.ccrb.statuslookup.web;

import static org.junit.Assert.assertEquals;
import gov.nyc.doitt.ccrb.statuslookup.test.BaseTest;
import gov.nyc.doitt.ccrb.statuslookup.util.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;



@RunWith(SpringJUnit4ClassRunner.class)
public class CaseControllerTests extends BaseTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	//private String formURL = "/ccrb-status-lookup/case/form";
	private String formURL = "/";
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testInvalidRequest() throws Exception {
		String expected = formURL + "?error=" + Constants.GET_ERROR_CODE;
		this.mockMvc.perform( get("/results"))
				.andDo(print())
				.andExpect(redirectedUrl(expected));
	}
	
	@Test
	public void testInvalidCaseID() throws Exception {
		String case_id = "1asdsd";
		String expected = formURL + "?error=" + Constants.CASE_ID_ERROR_CODE + "&case_id=" + case_id;
		this.mockMvc.perform( post("/results").param("case_id", case_id) )
				.andDo(print())
				.andExpect(redirectedUrl(expected));
	}
	
	@Test
	public void testvalidCaseID() throws Exception {
		String case_id = "12345";
		String expected = "viewcase";
		this.mockMvc.perform( post("/results").param("case_id", case_id) )
				.andDo(print())
				.andExpect(view().name(expected));
	}
}
