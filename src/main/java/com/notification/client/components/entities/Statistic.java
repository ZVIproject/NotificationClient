package com.notification.client.components.entities;

import java.util.Date;

public class Statistic {

	private Integer id;
	private Integer sendCount;
	private Integer failedCount;
	private Date created;
	private Date modified;
	
	
	public Statistic() {
		// default constructor
	}
	
	public Statistic(
			Integer id,
			Integer sendCount,
			Integer failedCount,
			Date created,
			Date modified
	) {
		this.id = id;
		this.sendCount = sendCount;
		this.failedCount = failedCount;
		this.created = created;
		this.modified = modified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
}
