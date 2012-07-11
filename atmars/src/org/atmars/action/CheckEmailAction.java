package org.atmars.action;

import java.util.Map;

import org.atmars.dao.User;
import org.atmars.service.Jmail;

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
		if(jmail.checkLink(u.getEmail(), u.getNickname(), u.getTime(), ticket))
		{
			u.setConfirm(true);
			u_service.updateUserInfo(u);
			Map session = ActionContext.getContext().getSession();
			session.put("user", u);
			return "confirm_success";
		}
		else{
			return "confirm_fail";
		}		
	}
}
