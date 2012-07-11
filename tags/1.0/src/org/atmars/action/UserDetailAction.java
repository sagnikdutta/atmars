package org.atmars.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.atmars.dao.User;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.opensymphony.xwork2.ActionContext;

public class UserDetailAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private List<User> search = null;
	private String searchString = "";

	private List<User> followings = null;

	private List<User> followers = null;

	public String search() {
		InitAction();
		search = new ArrayList<User>();
		if (searchString == null || searchString.isEmpty())
		{

		} 
		else 
		{
			if (currentUserFromSession == null)
			{
				search = uService.SearchUser(searchString, null);
			} 
			else 
			{
				search = uService.SearchUser(searchString,
						currentUserFromSession.getUserId());
			}
		}
		ActionContext.getContext().getSession().put("search", this.search);
		return "success";
	}

	public String myFollowings() {
		InitAction();
		followings = new ArrayList<User>();
		followings = uService.GetFollowings(currentUserFromSession.getUserId());
		ActionContext.getContext().getSession()
				.put("followings", this.followings);
		return "success";
	}

	public String myFollowers() {
		InitAction();
		followers = new ArrayList<User>();
		followers = uService.GetFollowers(currentUserFromSession.getUserId());
		ActionContext.getContext().getSession()
				.put("followers", this.followers);
		return "success";
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
