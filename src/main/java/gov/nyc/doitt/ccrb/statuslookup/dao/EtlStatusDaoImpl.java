package gov.nyc.doitt.ccrb.statuslookup.dao;


import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.nyc.doitt.ccrb.statuslookup.domain.EtlStatusEntity;

@Repository("etlstatusDAO")
@Transactional
public class EtlStatusDaoImpl implements EtlStatusDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public EtlStatusEntity getEtlStatus() {
		return (EtlStatusEntity) this.sessionFactory.getCurrentSession().createQuery("from EtlStatusEntity ORDER BY last_completed_date DESC").setMaxResults(1).uniqueResult();
	}

}
