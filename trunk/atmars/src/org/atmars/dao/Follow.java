package org.atmars.dao;

/**
 * Follow entity. @author MyEclipse Persistence Tools
 */

public class Follow implements java.io.Serializable {

	// Fields

	private Integer followId;
	private User userByFollowingId;
	private User userByFollowedId;

	// Constructors

	/** default constructor */
	public Follow() {
	}

	/** full constructor */
	public Follow(User userByFollowingId, User userByFollowedId) {
		this.userByFollowingId = userByFollowingId;
		this.userByFollowedId = userByFollowedId;
	}

	// Property accessors

	public Integer getFollowId() {
		return this.followId;
	}

	public void setFollowId(Integer followId) {
		this.followId = followId;
	}

	public User getUserByFollowingId() {
		return this.userByFollowingId;
	}

	public void setUserByFollowingId(User userByFollowingId) {
		this.userByFollowingId = userByFollowingId;
	}

	public User getUserByFollowedId() {
		return this.userByFollowedId;
	}

	public void setUserByFollowedId(User userByFollowedId) {
		this.userByFollowedId = userByFollowedId;
	}

}