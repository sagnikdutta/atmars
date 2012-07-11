package org.atmars.action;

import java.util.ArrayList;
import java.util.List;

import org.atmars.dao.Message;

public class GetMyMessageAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private int oldest_message_id;
	private List<Message> myMessages;
	
	@Override
	public String execute()
	{	
		InitAction();
		
		myMessages=m_service.GetMyMessages(current_usr_from_session.getUserId(), oldest_message_id);
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
