package org.atmars.service.interfaces;

import java.util.List;
import java.util.Set;

import org.atmars.dao.Comment;
import org.atmars.dao.Message;
import org.atmars.dao.MessageDAO;
import org.atmars.dao.Topic;

public interface MessageService {
	
	public MessageDAO getMessageDAO();
	
	public Message sendMessage(Integer userid, String text, String image, String position, int source);
	public Message getSingleMessage(Integer messageId);
	public List<Message> getUpdates(int userid, int oldest_message_id);
	public List<Message> GetOriginalMessages(int userId,int cursor);
	
	public Comment sendComment(Integer userid, Integer messageid, String text);
	List<Comment> getComment(int messageId, int cursor);
	
	public List<Message> findNewestMessagesFromAllUsers();
	
	
	public List<Message> GetMyMessages(int userId,int cursor);
}
