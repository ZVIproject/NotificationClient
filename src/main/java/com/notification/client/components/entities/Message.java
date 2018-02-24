package com.notification.client.components.entities;

import com.notification.client.utils.enums.MailStatus;

import java.util.Date;

public class Message {

	private Integer id;
	private String email;
	private Integer sendCount;
	private MailStatus status;
	private Date created;
	private Date modified;
	private Integer userId;
	
	
	public Message() {
		// default constructor
	}
	
	public Message(
			Integer id,
			String email,
			Integer sendCount,
			MailStatus status,
			Date created,
			Date modified,
			Integer userId
		) {
		this.id = id;
		this.email = email;
		this.sendCount = sendCount;
		this.status = status;
		this.created = created;
		this.modified = modified;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public MailStatus getStatus() {
		return status;
	}

	public void setStatus(MailStatus status) {
		this.status = status;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
