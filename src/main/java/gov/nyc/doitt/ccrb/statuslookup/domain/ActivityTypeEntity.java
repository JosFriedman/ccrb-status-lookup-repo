package gov.nyc.doitt.ccrb.statuslookup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACTIVITY_TYPE",schema="CCRBSL_MASTER")
public class ActivityTypeEntity {

	@Id
	@Column(name="ACTIVITY_TYPE_ID")
	private Integer activity_type_id;
	@Column(name="ACTIVITY_TYPE_DESCRIPTION")
	private String description;
	
	public Integer getActivity_type_id() {
		return activity_type_id;
	}

	public void setActivity_type_id(Integer activity_type_id) {
		this.activity_type_id = activity_type_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
