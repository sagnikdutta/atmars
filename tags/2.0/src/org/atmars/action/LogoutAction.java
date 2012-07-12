package org.atmars.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class LogoutAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		ActionContext.getContext().getSession().clear();
		return "success";
	}
}
