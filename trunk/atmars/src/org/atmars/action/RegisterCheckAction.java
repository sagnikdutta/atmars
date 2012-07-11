package org.atmars.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.atmars.dao.User;
import org.atmars.service.interfaces.UserService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.opensymphony.xwork2.ActionSupport;

public class RegisterCheckAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	// action form field
	private String nickname;
	private String email;
	private boolean occupied;

	public String nickname_occupied() {
		super.InitAction();
		User u = uService.getUserInfoByNickname(nickname);
		if (u==null) {
			occupied = false;
			return "success";
		}
		occupied = true;
		return "success";
	}

	public String email_occupied() {
		super.InitAction();
		User u = uService.getUserInfoByEmail(email);
		if (u==null) {
			occupied = false;
			return "success";
		}
		occupied = true;
		return "success";
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

}
