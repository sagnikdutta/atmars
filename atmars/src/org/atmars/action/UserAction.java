package org.atmars.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.atmars.dao.FollowDAO;
import org.atmars.dao.User;
import org.atmars.dao.UserDAO;
import org.atmars.service.UserServiceImpl;
import org.atmars.service.interfaces.UserService;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends BaseAction {

	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private String imageURI;

	private String email;
	private String password;
	private String nickname;
	private boolean gender;

	private int hisId;
	
	public String execute() {
		super.InitAction();
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		session.put("userList", u_service.GetNewRegisterUsers());
		return "success";
	}

	public String performRegister() throws IOException {
		
		super.InitAction();
		
		User u = new User();
		boolean avalible = true;
		Integer usr_id = (Integer) u_service.getId(this.email);
		if(usr_id!=null)
		{
			avalible= false;
		}
		List l = u_service.getUserDAO().findByNickname(this.nickname);
		if(l!=null&&l.size()>0)
		{
			avalible= false;
		}
		if (avalible) {
			u_service.register(email, password, nickname, gender);
			System.out.println(this.gender);
			u = (User) u_service.getUserInfoByEmail(this.email).get(0);
			u.setImage("image\\default.jpg");
			System.out.println("the user get");
			System.out.println(u.getUserId());
			if (upload != null) {
				System.out.println(this.uploadContentType);
				String user_dir = "image\\" + String.valueOf(u.getUserId())
						+ "\\";
				String dir = webRootPath + user_dir;
				if (new File(dir).mkdir()) {
					System.out.println("user direction created\n");
				}
				int index = uploadFileName.lastIndexOf(".");
				String fileType=uploadFileName.substring(index+1);
				System.out.println(fileType);
				String filename = String.valueOf(System.currentTimeMillis())+"."+fileType;
				System.out.println("file name is " + filename);
				String fn = dir + filename;
				FileOutputStream fos = new FileOutputStream(fn);
				InputStream is = new FileInputStream(upload);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				imageURI = "image/"+String.valueOf(u.getUserId())+"/"+filename;
				u.setImage(imageURI);

			} else {
				u.setImage("image/default.png");
			}
			u_service.updateUserInfo(u);

			ActionContext ctx = ActionContext.getContext();
			Map session = ctx.getSession();
			session.put("user", u);

			return "register_success";
		}
		return "error";
	}

	public String performLogin() {
		
		super.InitAction();
		
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		
		if (u_service.checkLogin(this.email, this.password)) {
			User u = (User) u_service.getUserInfoByEmail(this.email).get(0);
			session.put("user", u);		
			return "login_success";
		}
		
		String errorString="Sorry, the account with\n this keycode was not found.";
		session.put("error", errorString);		
		return "login_fail";
	}

	public String AddFollow() {
		
		super.InitAction();
		
		u_service.addFollowing(current_usr_from_session.getUserId(), this.hisId);
		
		return "add_follow_success";
		
	}

	public String getEmail() {
		return email;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getImageURI() {
		return imageURI;
	}

	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}


	

	public int getHisId() {
		return hisId;
	}

	public void setHisId(int hisId) {
		this.hisId = hisId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}
}
