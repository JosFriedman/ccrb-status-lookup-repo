package gov.nyc.doitt.ccrb.statuslookup.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="ACTIVITY",schema="CCRBSL_MASTER")
public class ActivityEntity {

	@Id
	@Column(name="ACTIVITY_ID")
	private Integer activity_id;
	@Column(name="ACTIVITY_COMPLETED_DATE")
	private Date completed_date;
	@Column(name="ACTIVITY_ORDER")
	private Integer order;
	
	@ManyToOne
	@JoinColumn(name="CASE_ID", referencedColumnName="case_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private CaseEntity parent_case;
	
	@ManyToOne
	@JoinColumn(name="ACTIVITY_TYPE_ID", referencedColumnName="activity_type_id", insertable=false, updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private ActivityTypeEntity type;

	public Integer getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}

	public Date getCompleted_date() {
		return completed_date;
	}

	public void setCompleted_date(Date completed_date) {
		this.completed_date = completed_date;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public CaseEntity getParent_case() {
		return parent_case;
	}

	public void setParent_case(CaseEntity parent_case) {
		this.parent_case = parent_case;
	}

	public ActivityTypeEntity getType() {
		return type;
	}

	public void setType(ActivityTypeEntity type) {
		this.type = type;
	}

	
	

}
