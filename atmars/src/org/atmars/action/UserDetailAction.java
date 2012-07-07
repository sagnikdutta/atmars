package org.atmars.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.atmars.dao.User;
import org.atmars.service.interfaces.MessageService;
import org.atmars.service.interfaces.UserService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class UserDetailAction extends ActionSupport {
	
	private String webRootPath=null;
	private MessageService m_service=null;
	private UserService u_service=null;
	private User current_usr_from_session=null;
	
	
	private ArrayList<User> search=null;
	private String searchString;
	
	private ArrayList<User> ganzhuderen=null;
	
	private ArrayList<User> wodefensi=null;
	
	
	public String search() {
		InitAction();
		search = new ArrayList<User>();
		String queryString = "from User u where u.nickname like '" + searchString + "'";
		List l = u_service.getUserDAO().getHibernateTemplate()
				.find(queryString);
		for (Object obj : l) {
			search.add((User) l);
		}
		
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		session.put("search", this.search);
		return "success";
	}
	
	public String myFollowings() {
		InitAction();
		ganzhuderen = new ArrayList<User>();
		int userId = current_usr_from_session.getUserId().intValue();
		String queryString = "select distinct u.email,u.nickname,u.gender,u.image u.followingCount u.followerCount u.postCount from User u,Follow f where f.userByFollowingId="
				+ userId;
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
			
			ganzhuderen.add(u);
		}
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		session.put("wodeguanzhu", this.ganzhuderen);
		return "success";
	}

	public String myFollowers() {
		InitAction();
		wodefensi = new ArrayList<User>();
		int userId = current_usr_from_session.getUserId().intValue();
		String queryString = "select distinct u.email,u.nickname,u.gender,u.image u.followingCount u.followerCount u.postCount from User u,Follow f where f.userByFollowedId="
				+ userId;
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
			
			wodefensi.add(u);
		}
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		session.put("following", this.wodefensi);
		return "success";
	}
	
	private void InitAction() {
		webRootPath = ServletActionContext.getServletContext().getRealPath("/");
		Resource res = new FileSystemResource(webRootPath
				+ "WEB-INF\\applicationContext.xml");
		XmlBeanFactory factory = new XmlBeanFactory(res);
		m_service = (MessageService) factory.getBean("messageService");
		u_service = (UserService) factory.getBean("userService");
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		current_usr_from_session = (User) session.get("user");
	}

	public ArrayList<User> getSearch() {
		return search;
	}

	public void setSearch(ArrayList<User> search) {
		this.search = search;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public ArrayList<User> getGanzhuderen() {
		return ganzhuderen;
	}

	public void setGanzhuderen(ArrayList<User> ganzhuderen) {
		this.ganzhuderen = ganzhuderen;
	}

	public ArrayList<User> getWodefensi() {
		return wodefensi;
	}

	public void setWodefensi(ArrayList<User> wodefensi) {
		this.wodefensi = wodefensi;
	}
	
}
