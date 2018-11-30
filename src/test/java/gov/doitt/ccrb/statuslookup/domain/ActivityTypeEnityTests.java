package gov.doitt.ccrb.statuslookup.domain;

import static org.junit.Assert.*;
import gov.nyc.doitt.ccrb.statuslookup.dao.CaseDao;
import gov.nyc.doitt.ccrb.statuslookup.domain.ActivityEntity;
import gov.nyc.doitt.ccrb.statuslookup.domain.ActivityTypeEntity;
import gov.nyc.doitt.ccrb.statuslookup.domain.CaseEntity;
import gov.nyc.doitt.ccrb.statuslookup.test.BaseTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivityTypeEnityTests extends BaseTest {

	@Autowired
	private CaseDao caseDao;

	private CaseEntity test_case = null;
	private ActivityEntity test_activity = null;
	private ActivityTypeEntity test_activity_type = null;
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
		test_activity_type = test_activity.getType();
	}
	
	@Test
	public void testGetActivity_type(){
		assertEquals(11, test_activity_type.getActivity_type_id().intValue());
	}
	
	@Test
	public void testDescription(){
		assertEquals("Forwarded to investigative team", test_activity_type.getDescription());
	}

}
