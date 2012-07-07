package org.atmars.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import sun.misc.BASE64Decoder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SendMessageAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String webRootPath = null;
	private MessageService m_service = null;
	private UserService u_service = null;
	private User current_usr_from_session = null;
	
	
	private String text;
	private int messageid=-1;
	private String position;
	private String upload;
	private String uploadFileName;
	private String imageURI;
	
	

	private Message lastPost=null;
	
	private void InitAction() {
		webRootPath = ServletActionContext.getServletContext().getRealPath("/");
		Resource res = new FileSystemResource(webRootPath
				+ "WEB-INF\\applicationContext.xml");
		XmlBeanFactory factory = new XmlBeanFactory(res);
		m_service = (MessageService) factory.getBean("messageService");
		u_service = (UserService) factory.getBean("userService");
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		current_usr_from_session = (User) session.get("user");
	}
	@Override
	public String execute() {
		InitAction();
		if(this.text==null)
		{
			return "error";
		}
		try {
			ImageUpload();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (imageURI != null) {
			lastPost = m_service.sendMessage(
					current_usr_from_session.getUserId(), text, imageURI,
					position, messageid);
		} else {
			lastPost = m_service.sendMessage(
					current_usr_from_session.getUserId(), text, null, position,
					messageid);
		}

		lastPost.setUser(current_usr_from_session);
		lastPost.getUser().setComments(null);
		lastPost.getUser().setFavorites(null);
		lastPost.getUser().setFollowsForFollowedId(null);
		lastPost.getUser().setFollowsForFollowingId(null);
		lastPost.getUser().setMessages(null);
		lastPost.setComments(null);
		lastPost.setFavorites(null);
		String timedes = TimeServiceImpl.getTimeDelay(lastPost.getTime());
		lastPost.setTimeDescription(timedes);
		String text2 = FaceServiceImpl.replaceFace(lastPost.getText());
		lastPost.setText(text2);
		
		
		return "success";
	}
	
	private boolean ImageUpload() throws IOException {
		
		this.imageURI=null;
		
		if (upload.equals("null")) {
			return false;
		}
		String user_dir = "image\\"
				+ String.valueOf(current_usr_from_session.getUserId()) + "\\";
		String dir = webRootPath + user_dir;
		if (new File(dir).exists()) {

		} else {
			new File(dir).mkdir();
			System.out.println("user direction created\n");
		}
		int index = uploadFileName.lastIndexOf(".");
		String fileType = uploadFileName.substring(index + 1);
		System.out.println(fileType);
		String filename = String.valueOf(System.currentTimeMillis()) + "."
				+ fileType;
		System.out.println("file name is " + filename);
		String fn = dir + filename;
		BASE64Decoder decoder = new BASE64Decoder();
		String upload2 = new String();
		int index1 = this.upload.indexOf('/');
		upload2 = this.upload.substring(index1 + 1);
		index1 = upload2.indexOf('/');
		String upload3 = upload2.substring(index1);
		byte[] b = decoder.decodeBuffer(upload3);

		FileOutputStream fos = new FileOutputStream(fn, true);
		fos.write(b);
		fos.flush();
		imageURI = "image/"
				+ String.valueOf(current_usr_from_session.getUserId()) + "/"
				+ filename;
		return true;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getMessageid() {
		return messageid;
	}
	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public Message getLastPost() {
		return lastPost;
	}
	public void setLastPost(Message lastPost) {
		this.lastPost = lastPost;
	}
}
