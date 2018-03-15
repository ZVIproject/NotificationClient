package com.notification.client.components.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notification.client.utils.enums.MailStatus;

import java.util.Date;

public class Message {

	private Integer id;
	private String email;
	private Integer sendCount;
	private MailStatus status;
	private Date created;
	private Date modified;
<<<<<<< HEAD
	private Boolean isBlackListed;
=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4

	@JsonProperty("userEntity")
	private User user;
	
	
	public Message() {}

	public Message(Integer id, String email, Integer sendCount, MailStatus status, Date created, Date modified, User user) {
		this.id = id;
		this.email = email;
		this.sendCount = sendCount;
		this.status = status;
		this.created = created;
		this.modified = modified;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
<<<<<<< HEAD

	public Boolean getBlackListed() {
		return isBlackListed;
	}

	public void setBlackListed(Boolean blackListed) {
		isBlackListed = blackListed;
	}
=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
}
