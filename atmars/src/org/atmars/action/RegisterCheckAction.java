package org.atmars.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.atmars.service.interfaces.UserService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.opensymphony.xwork2.ActionSupport;

public class RegisterCheckAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String webRootPath=null;
	private UserService usr_serv;
	
	
	//action form field
	private String nickname;
	private String email;
	private boolean occupied;
	
	public String nickname_occupied()
	{
		webRootPath = ServletActionContext.getServletContext()
				.getRealPath("/");

		Resource res = new FileSystemResource(webRootPath
				+ "WEB-INF\\applicationContext.xml");
		XmlBeanFactory factory = new XmlBeanFactory(res);
	    usr_serv = (UserService) factory.getBean("userService");
	    List l = usr_serv.getUserDAO().findByNickname(this.nickname);
	    
	    if(l==null||l.size()==0)
	    {
	    	occupied=false;
	    	return "success";
	    }
	    occupied = true;
	    return "success";
	}
	
	public String email_occupied()
	{
		webRootPath = ServletActionContext.getServletContext()
				.getRealPath("/");

		Resource res = new FileSystemResource(webRootPath
				+ "WEB-INF\\applicationContext.xml");
		XmlBeanFactory factory = new XmlBeanFactory(res);
	    usr_serv = (UserService) factory.getBean("userService");
	    List l = usr_serv.getUserInfoByEmail(this.email);
	    if(l==null||l.size()==0)
	    {
	    	occupied=false;
	    	return "success";
	    }
	    occupied=true;
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
