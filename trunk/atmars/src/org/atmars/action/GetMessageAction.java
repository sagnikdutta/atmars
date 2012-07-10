package org.atmars.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.atmars.dao.Message;
import org.atmars.dao.User;
import org.atmars.service.FaceServiceImpl;
import org.atmars.service.TimeServiceImpl;
import org.atmars.service.interfaces.MessageService;
import org.atmars.service.interfaces.UserService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetMessageAction extends BaseAction {
	
	//get my messages
	//action form field
	private int oldest_message_id;
	//return list json plugin input
	private ArrayList<Message> myMessages=null;
	
	
	//get weiboQiang messages;
	//json return input
	private ArrayList<Message> new_Messages=null;
	
	//get one newest message
	private int currentNewestMessageId=-1;
	private Message newestMessage_Now=null;
	
	
	public String weiboQiang() {
		InitAction();
		int qiangCount = 0;
		List l = m_service.findNewestMessagesFromAllUsers();
		new_Messages = new ArrayList<Message>();
		while (l != null && qiangCount < l.size()) {
			Message m = (Message) l.get(qiangCount);
			m.getUser().setPassword(null);
			m.getUser().setMessages(null);
			String timeDescription = TimeServiceImpl.getTimeDelay(m.getTime());
			m.setTimeDescription(timeDescription);
			String text2 = m.getText();
			String text3 = FaceServiceImpl.replaceFace(text2);
			m.setText(text3);
			new_Messages.add(m);
			qiangCount++;
		}
		return "success";
	}

	public String execute_getNewestMessage() {
		InitAction();
		List l = m_service.findNewestMessagesFromAllUsers();
		
		//there is no message
		if (l == null || l.size() == 0) {
			newestMessage_Now = new Message();
			newestMessage_Now.setUser(null);
			newestMessage_Now.setComments(null);
			newestMessage_Now.setFavorites(null);
			newestMessage_Now.setNewestState(false);
			
			return "success";
		}
		
		Message m = (Message) l.get(0);
		
		//if there is a new message
		if (m.getMessageId() > currentNewestMessageId) {
			newestMessage_Now = m;
			newestMessage_Now.setUser(m.getUser());
			newestMessage_Now.setComments(null);
			newestMessage_Now.setFavorites(null);
			newestMessage_Now.getUser().setComments(null);
			newestMessage_Now.getUser().setFavorites(null);
			newestMessage_Now.getUser().setFollowsForFollowedId(null);
			newestMessage_Now.getUser().setFollowsForFollowingId(null);
			newestMessage_Now.getUser().setMessages(null);
			newestMessage_Now.getUser().setPassword(null);
			
			String timeDescription = TimeServiceImpl.getTimeDelay(m.getTime());
			newestMessage_Now.setTimeDescription(timeDescription);
			String text2 = m.getText();
			String text3 = FaceServiceImpl.replaceFace(text2);
			newestMessage_Now.setText(text3);
			newestMessage_Now.setNewestState(true);
			return "success";
		}
		//there is no newest message
		newestMessage_Now = new Message();
		newestMessage_Now.setUser(null);
		newestMessage_Now.setComments(null);
		newestMessage_Now.setFavorites(null);
		newestMessage_Now.setNewestState(false);
		
		return "success";
	}
	
	
	
	public String execute_getMyMessages() throws Exception {
		InitAction();
		if (current_usr_from_session == null) {
			return "error";
		}
		List l = m_service.getUpdates(current_usr_from_session.getUserId()
					.intValue(), oldest_message_id);
		
		myMessages = new ArrayList<Message>();
		int i = 0;
		while (l != null && i < l.size()) {
			Object[] properties = (Object[]) l.get(i);
			Message m = new Message();
			m.setMessageId((Integer) properties[0]);
			User u = u_service.getUserInfo(((User) properties[1]).getUserId());
			User u2 = new User();
			u2.setUserId(u.getUserId());
			u2.setEmail(u.getEmail());
			u2.setNickname(u.getNickname());
			u2.setGender(u.getGender());
			u2.setImage(u.getImage());
			u2.setComments(null);
			u2.setFavorites(null);
			u2.setFollowsForFollowedId(null);
			u2.setFollowsForFollowingId(null);
			u2.setMessages(null);
			
			m.setUser(u2);
			m.setText((String) properties[2]);
			m.setImage((String) properties[3]);
			m.setTime((Date) properties[4]);
			m.setPosition((String) properties[5]);
			m.setSourceId((Integer) properties[6]);
			m.setCommentCount((Integer) properties[7]);
			m.setForwardCount((Integer) properties[8]);
			
			m.setComments(null);
			m.setFavorites(null);
			m.setFavorites(null);
			
			String timeDescription = org.atmars.service.TimeServiceImpl
					.getTimeDelay(m.getTime());
			m.setTimeDescription(timeDescription);
			String text2 = m.getText();
			String text3 = FaceServiceImpl.replaceFace(text2);
			m.setText(text3);
			
			m.setOriginal(null);
			myMessages.add(m);
			i++;
		}

		for (Message m : myMessages) {
			if (m.getSourceId() != -1) {
				Message m2 = m_service.getSingleMessage(m.getSourceId());
				if (m2 != null) {
					m2.getUser().setComments(null);
					m2.getUser().setFavorites(null);
					m2.getUser().setFollowsForFollowedId(null);
					m2.getUser().setFollowsForFollowingId(null);
					m2.getUser().setMessages(null);
					
					String timeDescription = org.atmars.service.TimeServiceImpl
							.getTimeDelay(m2.getTime());
					m2.setTimeDescription(timeDescription);
					String text2 = m2.getText();
					String text3 = FaceServiceImpl.replaceFace(text2);
					m2.setText(text3);
					m.setOriginal(m2);
				} 
			}
		}
		return "success";
	}
	
	

	public int getOldest_message_id() {
		return oldest_message_id;
	}

	public void setOldest_message_id(int oldest_message_id) {
		this.oldest_message_id = oldest_message_id;
	}

	public ArrayList<Message> getNew_Messages() {
		return new_Messages;
	}

	public void setNew_Messages(ArrayList<Message> new_Messages) {
		this.new_Messages = new_Messages;
	}

	public int getCurrentNewestMessageId() {
		return currentNewestMessageId;
	}

	public void setCurrentNewestMessageId(int currentNewestMessageId) {
		this.currentNewestMessageId = currentNewestMessageId;
	}

	public Message getNewestMessage_Now() {
		return newestMessage_Now;
	}

	public void setNewestMessage_Now(Message newestMessage_Now) {
		this.newestMessage_Now = newestMessage_Now;
	}

	public void setMyMessages(ArrayList<Message> myMessages) {
		this.myMessages = myMessages;
	}

	public ArrayList<Message> getMyMessages() {
		return myMessages;
	}
	
}
