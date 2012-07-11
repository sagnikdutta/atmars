package org.atmars.interceptor;

import java.util.Map;

import org.atmars.dao.User;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class AuthorizationInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		ActionContext ctx = arg0.getInvocationContext();
		Map session = ctx.getSession();
		User u  = (User)session.get("user");
		if(u !=null)
		{
			return arg0.invoke();
		}
		return "login";
	}

}
