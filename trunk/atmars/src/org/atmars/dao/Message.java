package org.atmars.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */

public class Message implements java.io.Serializable {

	// Fields


	private Message original=null;
	
	
	public Message getOriginal() {
		return original;
	}

	public void setOriginal(Message original) {
		this.original = original;
	}

	private boolean NewestState=true;
	
	

	public boolean getNewestState() {
		return NewestState;
	}

	public void setNewestState(boolean newestState) {
		NewestState = newestState;
	}

	private Integer messageId;
	private User user;
	private String text;
	private String image;
	private Date time;
	private String position;
	private Integer sourceId;
	private Integer commentCount;
	private Set favorites = new HashSet(0);
	private Set comments = new HashSet(0);

	private String timeDescription;
	// Constructors

	public String getTimeDescription() {
		return timeDescription;
	}

	public void setTimeDescription(String timeDescription) {
		this.timeDescription = timeDescription;
	}

	/** default constructor */
	public Message() {
	}

	/** minimal constructor */
	public Message(String text, Integer sourceId, Integer commentCount) {
		this.text = text;
		this.sourceId = sourceId;
		this.commentCount = commentCount;
	}

	/** full constructor */
	public Message(User user, String text, String image, Date time,
			String position, Integer sourceId, Integer commentCount,
			Set favorites, Set comments) {
		this.user = user;
		this.text = text;
		this.image = image;
		this.time = time;
		this.position = position;
		this.sourceId = sourceId;
		this.commentCount = commentCount;
		this.favorites = favorites;
		this.comments = comments;
	}

	// Property accessors

	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Set getFavorites() {
		return this.favorites;
	}

	public void setFavorites(Set favorites) {
		this.favorites = favorites;
	}

	public Set getComments() {
		return this.comments;
	}

	public void setComments(Set comments) {
		this.comments = comments;
	}

}