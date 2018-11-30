package gov.nyc.doitt.ccrb.statuslookup.service;

import gov.nyc.doitt.ccrb.statuslookup.dao.EtlStatusDao;
import gov.nyc.doitt.ccrb.statuslookup.domain.EtlStatusEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("etlStatusManager")
public class EtlStatusManagerImpl implements EtlStatusManager {

	@Autowired
	private EtlStatusDao etlStatusDao;

	@Override
	@Transactional
	public EtlStatusEntity getEtlStatus() {
		return etlStatusDao.getEtlStatus();
	}
}
