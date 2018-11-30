package gov.nyc.doitt.ccrb.statuslookup.dao;

import java.util.Calendar;
import java.util.Date;

import gov.nyc.doitt.ccrb.statuslookup.domain.CaseEntity;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("caseDAO")
@Transactional
public class CaseDaoImpl implements CaseDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public CaseEntity getCase(Integer id) {
		//Make sure result is lte 3 years
		Date today=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.YEAR, -3);
		Date ago = cal.getTime();
		
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(CaseEntity.class);
		criteria.add(Restrictions.eq("case_id", id));
		criteria.add(Restrictions.ge("recieved_date", ago));
		return (CaseEntity) criteria.uniqueResult();
	}
}
