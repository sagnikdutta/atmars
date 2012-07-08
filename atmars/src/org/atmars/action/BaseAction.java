package org.atmars.action;

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

public class BaseAction extends ActionSupport {
	protected String webRootPath=null;
	protected MessageService m_service=null;
	protected UserService u_service=null;
	protected User current_usr_from_session=null;
	
	protected void InitAction() {
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
}
