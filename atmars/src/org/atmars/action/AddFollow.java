package org.atmars.action;

import java.util.Map;

import org.atmars.dao.User;

import com.opensymphony.xwork2.ActionContext;

public class AddFollow extends BaseAction {
	private int hisId;
	private boolean isSuccess;
	private String description;

	@Override
	public String execute() {
		InitAction();
		if (u_service.addFollowing(current_usr_from_session.getUserId(), hisId)) {
			isSuccess = true;
			description = "success";
			return "success";
		} else {
			isSuccess = false;
			description = "already following";
		}
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		User u = u_service.getUserInfo(current_usr_from_session.getUserId());
		session.put("user", u);
		return "success";
	}

	public int getHisId() {
		return hisId;
	}

	public void setHisId(int hisId) {
		this.hisId = hisId;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
