package gov.nyc.doitt.ccrb.statuslookup.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name="CASE",schema="CCRBSL_MASTER")
public class CaseEntity {

	@Id
	@Column(name="CASE_ID")
	private Integer case_id;
	@Column(name="CASE_STATUS")
	private String status;
	@Column(name="CASE_PRECINCT")
	private String precinct;
	@Column(name="CASE_BORO")
	private String boro;
	@Column(name="CASE_INCIDENT_DATE")
	private Date incident_date;
	@Column(name="CASE_RECIEVED_DATE")
	private Date recieved_date;
	
	//list of activities associated to this case
	@OneToMany(fetch = FetchType.EAGER, mappedBy="parent_case")
	@OrderBy(clause="ACTIVITY_ORDER DESC")
	private List<ActivityEntity> activities;

	public Integer getCase_id() {
		return case_id;
	}

	public void setCase_id(Integer case_id) {
		this.case_id = case_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public String getBoro() {
		return boro;
	}

	public void setBoro(String boro) {
		this.boro = boro;
	}

	public Date getIncident_date() {
		return incident_date;
	}

	public void setIncident_date(Date incident_date) {
		this.incident_date = incident_date;
	}

	public Date getRecieved_date() {
		return recieved_date;
	}

	public void setRecieved_date(Date recieved_date) {
		this.recieved_date = recieved_date;
	}

	public List<ActivityEntity> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityEntity> activities) {
		this.activities = activities;
	}
	

}
