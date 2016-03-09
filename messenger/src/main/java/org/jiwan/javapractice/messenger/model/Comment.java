package org.jiwan.javapractice.messenger.model;

import java.util.Date;

public class Comment {
	private long id;
	private String message;
	private Date created;
	private String author;
	
	public Comment() {
		
		// TODO Auto-generated constructor stub
	}
	public Comment(long id, String message, String author) {
		this.created= new Date();
		this.id = id;
		this.message = message;
		this.author = author;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
