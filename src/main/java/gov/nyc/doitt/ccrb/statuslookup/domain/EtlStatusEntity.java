package gov.nyc.doitt.ccrb.statuslookup.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ETL_STATUS",schema="CCRBSL_MASTER")
public class EtlStatusEntity {

	@Id
	@Column(name="ETL_STATUS_ID")
	private Integer status_id;
	@Column(name="ETL_STATUS_RUNNING")
	private Integer running;
	@Column(name="ETL_STATUS_LAST_COMPLETED_DATE")
	private Date last_completed_date;
	@Column(name="ETL_STATUS_PROC_WID")
	private Integer proc_wid;
	
	public Integer getStatus_id() {
		return status_id;
	}
	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;
	}	
	public Integer getRunning() {
		return running;
	}
	public void setRunning(Integer running) {
		this.running = running;
	}
	public Date getLast_completed_date() {
		return last_completed_date;
	}
	public void setLast_completed_date(Date last_completed_date) {
		this.last_completed_date = last_completed_date;
	}
	public Integer getProc_wid() {
		return proc_wid;
	}
	public void setProc_wid(Integer proc_wid) {
		this.proc_wid = proc_wid;
	}
	
	
	
}
