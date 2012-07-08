package org.atmars.action;

import org.atmars.dao.Comment;

import com.opensymphony.xwork2.ActionSupport;

public class SendCommentAction extends BaseAction {

	private int messageId;
	private String commentString;

	private Comment _publishedComment;
	@Override
	public String execute() 
	{
		
		InitAction();
		
		_publishedComment =  m_service.sendComment(current_usr_from_session.getUserId(), messageId, commentString);
		System.out.println(_publishedComment.getUser().getUserId());
		
		return "success";
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getCommentString() {
		return commentString;
	}
	public void setCommentString(String commentString) {
		this.commentString = commentString;
	}
	public Comment get_publishedComment() {
		return _publishedComment;
	}
	public void set_publishedComment(Comment _publishedComment) {
		this._publishedComment = _publishedComment;
	}

}
