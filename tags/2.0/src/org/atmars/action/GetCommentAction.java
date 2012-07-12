package org.atmars.action;

import java.util.List;

import org.atmars.dao.Comment;
import org.atmars.utils.*;

public class GetCommentAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private int messageId;
	private int cursor = -1;

	private List<Comment> comments;

	@Override
	public String execute() {
		InitAction();

		comments = mService.getComment(messageId, cursor);
		for (Comment c : comments) {
			c.MakeAllSetNull();
			c.setTimeDescription(TimeUtils.getTimeDelay(c.getTime()));
		}

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
