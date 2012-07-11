package org.atmars.action;

import java.util.List;

import org.atmars.dao.Message;
import org.atmars.utils.ConvertPostUtils;
import org.atmars.utils.TimeUtils;

public class GetOriginalMessages extends BaseAction {

	private int userId = -1;
	private int cursor = -1;
	private List<Message> originalList;

	@Override
	public String execute() {
		InitAction();
		if (userId == -1) {
			userId = currentUserFromSession.getUserId();
		}
		originalList = mService.GetOriginalMessages(userId, cursor);
		for (Message m : originalList) {
			m.MakeAllSetNull();
			m.getUser().setPassword(null);
			m.setTimeDescription(TimeUtils.getTimeDelay(m.getTime()));
			m.setText(ConvertPostUtils.replaceFace(m.getText()));
			m.setText(ConvertPostUtils.replaceAtMarkToHTML(m.getText()));
		}
		return "success";
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public List<Message> getOriginalList() {
		return originalList;
	}

	public void setOriginalList(List<Message> originalList) {
		this.originalList = originalList;
	}

}
