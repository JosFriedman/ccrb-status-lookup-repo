package gov.nyc.doitt.ccrb.statuslookup.service;

import gov.nyc.doitt.ccrb.statuslookup.dao.CaseDao;
import gov.nyc.doitt.ccrb.statuslookup.domain.CaseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service("caseManager")
public class CaseManagerImpl implements CaseManager {

	@Autowired
	private CaseDao caseDao;
	
	@Override
	@Transactional
	public CaseEntity getCase(Integer case_id) {
		return caseDao.getCase(case_id);
	}
}
