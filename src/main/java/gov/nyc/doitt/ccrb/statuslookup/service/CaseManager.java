package gov.nyc.doitt.ccrb.statuslookup.service;

import gov.nyc.doitt.ccrb.statuslookup.domain.CaseEntity;

public interface CaseManager {
	public CaseEntity getCase(Integer case_id);
}
