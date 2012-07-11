package org.atmars.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.atmars.dao.Message;
import org.atmars.dao.User;
import org.atmars.service.interfaces.MessageService;
import org.atmars.service.interfaces.UserService;
import org.atmars.utils.ConvertPostUtils;
import org.atmars.utils.TimeUtils;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import sun.misc.BASE64Decoder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SendMessageAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String text;
	private int messageid = -1;
	private String position;
	private String upload;
	private String uploadFileName;
	private String imageURI;

	// return lastPost;
	private Message lastPost = null;

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
			lastPost = mService.sendMessage(
					currentUserFromSession.getUserId(), text, imageURI,
					position, messageid);
		} else {
			lastPost = mService.sendMessage(
					currentUserFromSession.getUserId(), text, null, position,
					messageid);
		}
		lastPost.MakeAllSetNull();
		lastPost.getUser().setPassword(null);
		if(lastPost.getOriginal()!=null)
		{
			lastPost.getOriginal().MakeAllSetNull();
			lastPost.getUser().setPassword(null);
		}
		lastPost.setTimeDescription(TimeUtils.getTimeDelay(lastPost.getTime()));
		lastPost.setText(ConvertPostUtils.replaceFace(lastPost.getText()));
		lastPost.setText(ConvertPostUtils.replaceAtMarkToHTML(lastPost.getText()));
		User u = uService.getUserInfoByUserId(currentUserFromSession.getUserId());
		ActionContext.getContext().getSession().put("user", u);
		return "success";
	}

	private boolean ImageUpload() throws IOException {

		this.imageURI = null;

		if (upload.equals("null")) {
			return false;
		}
		String user_dir = "image\\"
				+ String.valueOf(currentUserFromSession.getUserId()) + "\\";
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
				+ String.valueOf(currentUserFromSession.getUserId()) + "/"
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
