package org.atmars.action;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.atmars.dao.Message;

public class MobileSendMessageAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private int userId;
	private int forwordId=-1;
	private String text;
	private String ticket;
	private Message message=null;
	public String execute()
	{
		InitAction();
		if(uService.AuthenticUserWithTicket(userId, ticket))
		{
			message = mService.sendMessage(userId, text, null, null, forwordId);
			message.MakeAllSetNull();
			message.getUser().setPassword(null);
			if(message.getOriginal()!=null)
			{
				message.getOriginal().MakeAllSetNull();
				message.getOriginal().getUser().setPassword(null);
			}
		}
		return "success";
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getForwordId() {
		return forwordId;
	}
	public void setForwordId(int forwordId) {
		this.forwordId = forwordId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
}
