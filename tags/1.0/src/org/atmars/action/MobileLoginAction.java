package org.atmars.action;

import java.io.UnsupportedEncodingException;

import org.atmars.dao.User;

public class MobileLoginAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private User user=null;
	private String ticket=null;
	
	@Override
	public String execute()
	{
		InitAction();
		if(uService.checkLogin(email, password))
		{
			user= uService.getUserInfoByEmail(email);
			try {
				ticket=uService.CalculateTicket(user);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		user.MakeAllSetNull();
		user.setPassword(null);
		return "success";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
