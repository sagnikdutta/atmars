package org.atmars.action;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.atmars.dao.User;
import org.atmars.service.Jmail;
import org.hibernate.classic.Session;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionContext;

public class CheckEmailAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String email;
	private String ticket;
	@Override
	public String execute()
	{
		InitAction();
		Jmail jmail = new Jmail();
		User u = (User) u_service.getUserInfoByEmail(email).get(0);
		try {
			if(jmail.checkLink(u.getEmail(), u.getNickname(), u.getTime(), ticket))
			{
				u.setConfirm(true);
				u_service.updateUserInfo(u);
				Map session = ActionContext.getContext().getSession();
				session.put("user", u);
				return "confirm_success";
			}
			else{
				u_service.getUserDAO().getHibernateTemplate().delete(u);
				return "confirm_fail";
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
