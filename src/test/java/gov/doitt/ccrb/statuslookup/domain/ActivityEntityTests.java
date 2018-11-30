package gov.doitt.ccrb.statuslookup.domain;


import gov.nyc.doitt.ccrb.statuslookup.dao.CaseDao;
import gov.nyc.doitt.ccrb.statuslookup.domain.ActivityEntity;
import gov.nyc.doitt.ccrb.statuslookup.domain.CaseEntity;
import gov.nyc.doitt.ccrb.statuslookup.test.BaseTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActivityEntityTests extends BaseTest{

	@Autowired
	private CaseDao caseDao;

	private CaseEntity test_case = null;
	private ActivityEntity test_activity = null;
	private SimpleDateFormat sdf = null;
	private Date compared_date = null;
	
	@Before
	public void setUp() throws ParseException {
		//note: if this case fails, it might not be in the result set. 
		test_case = caseDao.getCase(201510144);
		sdf = new SimpleDateFormat("MM-dd-yyyy");
		compared_date = sdf.parse("01-01-1999");
		//gets first activity
		test_activity = test_case.getActivities().get(0);
	}
	
	@Test
	public void testGetActivity_id () {
		System.out.println(test_activity.getActivity_id());
		assertEquals(299158, test_activity.getActivity_id().intValue());
	}
	
	@Test
	public void testGetCompleted_date () {
		assertTrue(test_activity.getCompleted_date().compareTo(compared_date) > 0);
	}
	
	@Test
	public void testGetOrder () {
		assertEquals(2, test_activity.getOrder().intValue());
	}

	@Test
	public void testParentCase () {
		assertEquals(201510144, test_activity.getParent_case().getCase_id().intValue());
	}
	
}
