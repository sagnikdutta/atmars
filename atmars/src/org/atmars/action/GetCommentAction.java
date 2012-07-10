package org.atmars.action;

import java.util.List;

import org.atmars.dao.Comment;

public class GetCommentAction extends BaseAction {

	private int messageId;
	private int cursor=9999999;
	
	private List<Comment> comments;
	
	@Override
	public String execute()
	{
		InitAction();
		
		comments = m_service.getComment(messageId, cursor);
		
		return "success";
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
