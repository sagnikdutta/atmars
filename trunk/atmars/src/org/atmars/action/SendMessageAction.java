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

public class SendMessageAction extends BaseAction{
	
	private String text;
	private int messageid=-1;
	private String position;
	private String upload;
	private String uploadFileName;
	private String imageURI;
	
	//return lastPost;
	private Message lastPost=null;
	
	@Override
	public String execute() {
		InitAction();
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
		
		if(lastPost.getSourceId()!=-1)
		{
			Message original = m_service.getSingleMessage(lastPost.getSourceId());
			
			original.setTimeDescription(TimeServiceImpl.getTimeDelay(original.getTime()));
			original.setText(FaceServiceImpl.replaceFace(original.getText()));
			
			original.setComments(null);
			original.setFavorites(null);
			lastPost.setOriginal(original);
		}
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		User u = u_service.getUserInfo(current_usr_from_session.getUserId());
		session.put("user", u);
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
		int index1 = this.upload.indexOf(',');
		upload2 = this.upload.substring(index1 + 1);
		byte[] b = decoder.decodeBuffer(upload2);

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
