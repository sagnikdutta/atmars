package org.atmars.action;

import java.util.ArrayList;
import java.util.List;

import org.atmars.dao.Message;
import org.atmars.utils.ConvertPostUtils;
import org.atmars.utils.TimeUtils;

public class GetMyMessageAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private int oldest_message_id;
	private List<Message> myMessages;

	@Override
	public String execute() {
		InitAction();

		myMessages = mService.GetMyMessages(
				currentUserFromSession.getUserId(), oldest_message_id);
		for (Message m : myMessages) {
			m.MakeAllSetNull();
			m.getUser().setPassword(null);
			m.setTimeDescription(TimeUtils.getTimeDelay(m.getTime()));
			m.setText(ConvertPostUtils.replaceFace(m.getText()));
			m.setText(ConvertPostUtils.replaceAtMarkToHTML(m.getText()));
		}
		return "success";
	}

	public int getOldest_message_id() {
		return oldest_message_id;
	}

	public void setOldest_message_id(int oldest_message_id) {
		this.oldest_message_id = oldest_message_id;
	}

	public List<Message> getMyMessages() {
		return myMessages;
	}

	public void setMyMessages(List<Message> myMessages) {
		this.myMessages = myMessages;
	}

}
