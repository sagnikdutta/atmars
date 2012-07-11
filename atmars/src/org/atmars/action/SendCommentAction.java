package org.atmars.action;

import org.atmars.dao.Comment;
import org.atmars.utils.TimeUtils;

import com.opensymphony.xwork2.ActionSupport;

public class SendCommentAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private int messageId;
	private String commentString;

	private Comment _publishedComment;

	@Override
	public String execute() {

		InitAction();

		_publishedComment = mService.sendComment(
				currentUserFromSession.getUserId(), messageId, commentString);
		_publishedComment.setTimeDescription(TimeUtils
				.getTimeDelay(_publishedComment.getTime()));
		_publishedComment.MakeAllSetNull();

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
