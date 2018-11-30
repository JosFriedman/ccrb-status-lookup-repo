package gov.doitt.ccrb.statuslookup.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import gov.nyc.doitt.ccrb.statuslookup.dao.EtlStatusDao;
import gov.nyc.doitt.ccrb.statuslookup.domain.EtlStatusEntity;
import gov.nyc.doitt.ccrb.statuslookup.test.BaseTest;

public class EtlStatusDaoTests extends BaseTest {

	@Autowired
	private EtlStatusDao etlstatusDao;
	
	private EtlStatusEntity etl_status = null;

	@Before
	public void setUp() {
		etl_status = etlstatusDao.getEtlStatus();
	}
	
	//Getter Tests	
	@Test
	public void testGetEtlStatusCase() {
		assertTrue(etl_status.getRunning().equals(0));
	}
	
	@Test
	public void testGetEtlStatusId() {
		assertTrue(etl_status.getStatus_id() >= 1);
	}
	
	@Test
	public void testGetProc_wid() {
		assertTrue(etl_status.getProc_wid() >= 1);
	}
	
	@Test
	public void testGetLastCompletedDate() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date compared_date = sdf.parse("01-01-1999");
		assertTrue(etl_status.getLast_completed_date().compareTo(compared_date) > 0);
	}
}
