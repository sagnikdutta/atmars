package org.atmars.interceptor;

import java.util.Map;

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
		String email = (String)session.get("email");
		if(email !=null && !email.equals(""))
		{
			return arg0.invoke();
		}
		return Action.LOGIN;
	}

}
