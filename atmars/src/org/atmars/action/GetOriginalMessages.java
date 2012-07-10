package org.atmars.action;

import java.util.List;

import org.atmars.dao.Message;
import org.atmars.service.FaceServiceImpl;

public class GetOriginalMessages extends BaseAction {
	
	private int userId=-1;
	private int cursor=-1;
	private List<Message> originalList;
@Override
public String execute()
{
	InitAction();
	if(userId==-1)
	{
		userId=current_usr_from_session.getUserId();
	}
	originalList = m_service.GetOriginalMessages(userId, cursor);
	for (Message m : originalList) {
		if (m.getSourceId() != -1) {
			Message m2 = m_service.getSingleMessage(m.getSourceId());
			if (m2 != null) {
				m2.MakeAllSetNull();
				
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
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public int getCursor() {
	return cursor;
}
public void setCursor(int cursor) {
	this.cursor = cursor;
}
public List<Message> getOriginalList() {
	return originalList;
}
public void setOriginalList(List<Message> originalList) {
	this.originalList = originalList;
}

}
