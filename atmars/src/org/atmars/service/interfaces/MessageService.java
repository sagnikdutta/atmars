package org.atmars.service.interfaces;

import java.util.List;
import java.util.Set;

import org.atmars.dao.Comment;
import org.atmars.dao.Message;
import org.atmars.dao.MessageDAO;
import org.atmars.dao.Topic;
import org.atmars.dao.UserDAO;

public interface MessageService {
	
	public Message sendMessage(Integer userid, String text, String image, String position, int source);
	public Message getSingleMessage(Integer id);
	public void sendComment(Integer userid, Integer messageid, String text);
	public Set<Comment> getComment(Integer messageid);
	public List<Message> searchMessage(Integer userid, String query);
	public void addFavoriteMessage(Integer userid, Integer messageid);
	public List<Topic> getHotTopic();
	public List getMessage(int userid, int oldest_message_id);
	public List getMessage(int intValue);
	public List findNewestMessageS();
	public MessageDAO getMessageDAO();
	//public void removeFavoriteMessage(Integer user_id, Integer message_id);
}
