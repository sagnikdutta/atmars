package org.atmars.dao;

import java.util.Date;

/**
 * Favorite entity. @author MyEclipse Persistence Tools
 */

public class Favorite implements java.io.Serializable {

	// Fields

	private Integer favoriteId;
	private User user;
	private Message message;
	private Date time;

	// Constructors

	/** default constructor */
	public Favorite() {
	}

	/** minimal constructor */
	public Favorite(Date time) {
		this.time = time;
	}

	/** full constructor */
	public Favorite(User user, Message message, Date time) {
		this.user = user;
		this.message = message;
		this.time = time;
	}

	// Property accessors

	public Integer getFavoriteId() {
		return this.favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	public void MakeAllSetNull()
	{
		this.user.MakeAllSetNull();
		this.message.MakeAllSetNull();
	}
}