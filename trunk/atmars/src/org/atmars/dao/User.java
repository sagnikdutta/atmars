package org.atmars.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String email;
	private String password;
	private String nickname;
	private boolean gender;
	private String image;
	private Integer priority;
	private Date time;
	private Set messages = new HashSet(0);
	private Set followsForFollowingId = new HashSet(0);
	private Set comments = new HashSet(0);
	private Set favorites = new HashSet(0);
	private Set followsForFollowedId = new HashSet(0);

	
	private int followingsCount;
	private int followedCount;
	private int postCount;
	// Constructors

	public int getFollowingsCount() {
		return followingsCount;
	}

	public void setFollowingsCount(int followingsCount) {
		this.followingsCount = followingsCount;
	}

	public int getFollowedCount() {
		return followedCount;
	}

	public void setFollowedCount(int followedCount) {
		this.followedCount = followedCount;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String email, String password, String nickname, boolean gender) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
	}

	/** full constructor */
	public User(String email, String password, String nickname, boolean gender,
			String image, Integer priority, Date time, Set messages,
			Set followsForFollowingId, Set comments, Set favorites,
			Set followsForFollowedId) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
		this.image = image;
		this.priority = priority;
		this.time = time;
		this.messages = messages;
		this.followsForFollowingId = followsForFollowingId;
		this.comments = comments;
		this.favorites = favorites;
		this.followsForFollowedId = followsForFollowedId;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean getGender() {
		return this.gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Set getMessages() {
		return this.messages;
	}

	public void setMessages(Set messages) {
		this.messages = messages;
	}

	public Set getFollowsForFollowingId() {
		return this.followsForFollowingId;
	}

	public void setFollowsForFollowingId(Set followsForFollowingId) {
		this.followsForFollowingId = followsForFollowingId;
	}

	public Set getComments() {
		return this.comments;
	}

	public void setComments(Set comments) {
		this.comments = comments;
	}

	public Set getFavorites() {
		return this.favorites;
	}

	public void setFavorites(Set favorites) {
		this.favorites = favorites;
	}

	public Set getFollowsForFollowedId() {
		return this.followsForFollowedId;
	}

	public void setFollowsForFollowedId(Set followsForFollowedId) {
		this.followsForFollowedId = followsForFollowedId;
	}

}