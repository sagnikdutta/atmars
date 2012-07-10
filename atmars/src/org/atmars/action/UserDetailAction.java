package org.atmars.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.atmars.dao.User;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.opensymphony.xwork2.ActionContext;

public class UserDetailAction extends BaseAction {

	private ArrayList<User> search=null;
	private String searchString="";

	private ArrayList<User> followings = null;

	private ArrayList<User> followers = null;

	public String search() {
		InitAction();
		search = new ArrayList<User>();
		
		if (!searchString.isEmpty()) {
			String queryString = "from User u where u.nickname like '%"
					+ searchString + "%'";
			Session s = u_service.getUserDAO().getHibernateTemplate().getSessionFactory().openSession();
			Query q = s.createQuery(queryString);
			List l = q.list();
			for (Object obj : l) {
				search.add((User) obj);
			}
		}
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		session.put("search", this.search);
		return "success";
	}

	public String myFollowings() {
		InitAction();
		followings = new ArrayList<User>();
		int userId = current_usr_from_session.getUserId().intValue();
		String queryString = "select distinct u.email,u.nickname,u.gender,u.image, u.followingCount,u.followerCount, u.postCount from User u,Follow f where "
		+"u.userId=f.userByFollowedId"
				+" and f.userByFollowingId="
				+ userId
				+ " and f.userByFollowedId!=" + userId;
		List l = u_service.getFollowDAO().getHibernateTemplate()
				.find(queryString);
		for (Object obj : l) {
			Object[] o = (Object[]) obj;
			User u = new User();
			u.setEmail((String) o[0]);
			u.setNickname((String) o[1]);
			u.setGender((Boolean) o[2]);
			u.setImage((String) o[3]);

			u.setFollowingCount((Integer) o[4]);
			u.setFollowerCount((Integer) o[5]);
			u.setPostCount((Integer) o[6]);

			followings.add(u);
		}
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		session.put("followings", this.followings);
		return "success";
	}

	public String myFollowers() {
		InitAction();
		followers = new ArrayList<User>();
		int userId = current_usr_from_session.getUserId().intValue();
		String queryString = "select distinct u.email,u.nickname,u.gender,u.image ,u.followingCount, u.followerCount, u.postCount from User u,Follow f where "
				+"f.userByFollowingId=u.userId"
				+" and f.userByFollowedId="+userId
				+ " and f.userByFollowingId!=" + userId;
		List l = u_service.getFollowDAO().getHibernateTemplate()
				.find(queryString);
		for (Object obj : l) {
			Object[] o = (Object[]) obj;
			User u = new User();
			u.setEmail((String) o[0]);
			u.setNickname((String) o[1]);
			u.setGender((Boolean) o[2]);
			u.setImage((String) o[3]);

			u.setFollowingCount((Integer) o[4]);
			u.setFollowerCount((Integer) o[5]);
			u.setPostCount((Integer) o[6]);

			followers.add(u);
		}
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		session.put("followers", this.followers);
		return "success";
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
