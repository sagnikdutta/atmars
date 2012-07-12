package org.atmars.action;

import java.util.List;
import org.atmars.dao.Message;
import org.atmars.utils.ConvertPostUtils;
import org.atmars.utils.TimeUtils;

public class MobileGetMyMessagesAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private int oldestMessageId=-1;
	private List<Message> myMessages;
	private int userId;
	private String ticket;
	
	@Override
	public String execute() {
		InitAction();

		if(uService.AuthenticUserWithTicket(userId, ticket))
		{
			myMessages = mService.GetMyMessages(
					userId, oldestMessageId);
			for (Message m : myMessages) {
				m.MakeAllSetNull();
				m.getUser().setPassword(null);
				m.setTimeDescription(TimeUtils.getTimeDelay(m.getTime()));
				m.setText(ConvertPostUtils.replaceFace(m.getText()));
				m.setText(ConvertPostUtils.replaceAtMarkToHTML(m.getText()));
				if(m.getOriginal()!=null)
				{
					m.getOriginal().MakeAllSetNull();
					m.getOriginal().getUser().setPassword(null);
					m.getOriginal().setText(ConvertPostUtils.replaceFace(m.getOriginal().getText()));
					m.getOriginal().setText(ConvertPostUtils.replaceAtMarkToHTML(m.getOriginal().getText()));
					m.getOriginal().setTimeDescription(TimeUtils.getTimeDelay(m.getOriginal().getTime()));
				}
			}
			return "success";
		}
		myMessages=null;
		return "success";
	}

	public int getOldestMessageId() {
		return oldestMessageId;
	}

	public void setOldestMessageId(int oldestMessageId) {
		this.oldestMessageId = oldestMessageId;
	}

	public List<Message> getMyMessages() {
		return myMessages;
	}

	public void setMyMessages(List<Message> myMessages) {
		this.myMessages = myMessages;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
