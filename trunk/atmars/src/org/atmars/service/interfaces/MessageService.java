package org.atmars.service.interfaces;

import java.util.List;
import org.atmars.dao.Comment;
import org.atmars.dao.Message;

public interface MessageService {
	public Message sendMessage(Integer userid, String text, String image, String position, int source);
	public Message getSingleMessage(Integer messageId);
	public List<Message> GetOriginalMessages(int userId,int cursor);
	public Comment sendComment(Integer userid, Integer messageid, String text);
	public List<Comment> getComment(int messageId, int cursor);
	public List<Message> findNewestMessagesFromAllUsers();
	public List<Message> GetMyMessages(int userId,int cursor);
}
