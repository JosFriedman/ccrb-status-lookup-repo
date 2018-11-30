package gov.doitt.ccrb.statuslookup.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nyc.doitt.ccrb.statuslookup.dao.CaseDao;
import gov.nyc.doitt.ccrb.statuslookup.dao.CaseDaoImpl;
import gov.nyc.doitt.ccrb.statuslookup.domain.CaseEntity;
import gov.nyc.doitt.ccrb.statuslookup.test.BaseTest;

public class CaseDaoTests extends BaseTest {

	@Autowired
	private CaseDao caseDao;

	private CaseEntity test_case = null;
	private SimpleDateFormat sdf = null;
	private Date compared_date = null;
	
	@Before
	public void setUp() throws ParseException {
		
		CaseDaoImpl.yearsLookBack = 20;

		//note: if this case fails, it might not be in the result set. 
		test_case = caseDao.getCase(201510144);
		sdf = new SimpleDateFormat("MM-dd-yyyy");
		compared_date = sdf.parse("01-01-1999");
		
	}
	
	@Test
	public void testGetCase_id() {
		assertTrue(test_case.getCase_id().compareTo(201510144) == 0);
	}
	
	@Test
	public void testGetBoro() {
		assertEquals("Queens",test_case.getBoro().trim());
	}
	
	@Test
	public void testGetPrecinct() {
		assertEquals("109", test_case.getPrecinct().trim());
	}
	
	@Test
	public void testGetStatus() {
		assertEquals("Open", test_case.getStatus());
	}
	
	@Test
	public void testGetActivities() {
		assertEquals(2, test_case.getActivities().size());
	}

	@Test
	public void testGetRecieved_date() {
		assertTrue(test_case.getRecieved_date().compareTo(compared_date) > 0);
	}

	@Test
	public void testGetIncident_date() {
		assertTrue(test_case.getIncident_date().compareTo(compared_date) > 0);
	}

	@Test public void testRecievedDateTooOld() {

		// put in future 
		CaseDaoImpl.yearsLookBack = -1;

		test_case = caseDao.getCase(201510144);
		assertNull(test_case);

	}
}
