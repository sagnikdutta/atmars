package org.atmars.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.atmars.dao.Comment;
import org.atmars.dao.CommentDAO;
import org.atmars.dao.FavoriteDAO;
import org.atmars.dao.Message;
import org.atmars.dao.MessageDAO;
import org.atmars.dao.Topic;
import org.atmars.dao.TopicDAO;
import org.atmars.dao.User;
import org.atmars.dao.UserDAO;
import org.atmars.service.MessageServiceImpl;
import org.atmars.service.TimeServiceImpl;
import org.atmars.service.UserServiceImpl;
import org.atmars.service.interfaces.MessageService;
import org.atmars.service.interfaces.UserService;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import sun.misc.BASE64Decoder;

import com.opensymphony.xwork2.ActionContext;

public class homeAction {
	private String webRootPath;
	private MessageService m_service;
	private UserService u_service;

	private List<Message> myMessages;
	private Map<String, Message> not_original;
	private List<Message> new_Messages;

	private int qiangCount;
	private int oldest_message_id = -1;
	private String latitude;
	private String longitude;
	private int source_id = -1;
	private String text = null;
	private Message lastPost = null;

	private int currentNewestMessageId = -1;

	private String upload;
	private String uploadFileName;
	private String uploadContentType;
	private String imageURI;

	private Map session = null;

	private Message newestMessage_Now = null;
	private User current_usr_from_session = null;
	private Integer messageid;
	private Set<Comment> comments;
	private List<Topic> hotTopics;

	private String position = new String();

	private List<User> search;
	private String searchString;

	public String search() {
		InitAction();
		search = new ArrayList<User>();
		int userId = current_usr_from_session.getUserId().intValue();
		String queryString = "select distinct u.email,u.nickname,u.gender,u.image from User u where u.nickname like '"
				+ searchString + "'";
		System.out.println(queryString);
		List l = u_service.getUserDAO().getHibernateTemplate()
				.find(queryString);
		for (Object obj : l) {
			Object[] o = (Object[]) obj;
			User u = new User();
			u.setEmail((String) o[0]);
			u.setNickname((String) o[1]);
			u.setGender((Boolean) o[2]);
			u.setImage((String) o[3]);
			search.add(u);
		}
		for (User u : search) {
			u.setFollowingsCount(3);
			u.setFollowedCount(3);
			u.setPostCount(1);
		}
		session.put("search", this.search);
		return "success";
	}

	public List<User> getSearch() {
		return search;
	}

	public void setSearch(List<User> search) {
		this.search = search;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public List<User> getGanzhuderen() {
		return ganzhuderen;
	}

	public void setGanzhuderen(List<User> ganzhuderen) {
		this.ganzhuderen = ganzhuderen;
	}

	private List<User> ganzhuderen;

	public String wodeguanzhu() {
		InitAction();
		ganzhuderen = new ArrayList<User>();
		int userId = current_usr_from_session.getUserId().intValue();
		String queryString = "select distinct u.email,u.nickname,u.gender,u.image from User u,Follow f where f.userByFollowingId="
				+ userId;
		List l = u_service.getFollowDAO().getHibernateTemplate()
				.find(queryString);
		for (Object obj : l) {
			Object[] o = (Object[]) obj;
			User u = new User();
			u.setEmail((String) o[0]);
			u.setNickname((String) o[1]);
			u.setGender((Boolean) o[2]);
			u.setImage((String) o[3]);
			ganzhuderen.add(u);
		}
		for (User u : ganzhuderen) {
			u.setFollowingsCount(3);
			u.setFollowedCount(3);
			u.setPostCount(1);
		}
		session.put("followers", this.ganzhuderen);
		return "success";
	}

	private List<User> fensi;

	public String chaFensi() {
		InitAction();
		fensi = new ArrayList<User>();
		int userId = current_usr_from_session.getUserId().intValue();
		String queryString = "select distinct u.email,u.nickname,u.gender,u.image from User u,Follow f where f.userByFollowedId="
				+ userId;
		System.out.println("差粉丝" + queryString);
		List l = u_service.getFollowDAO().getHibernateTemplate()
				.find(queryString);
		System.out.println("查到粉丝数" + l.size());
		for (Object obj : l) {
			Object[] o = (Object[]) obj;
			User u = new User();
			u.setEmail((String) o[0]);
			u.setNickname((String) o[1]);
			u.setGender((Boolean) o[2]);
			u.setImage((String) o[3]);
			fensi.add(u);
		}
		System.out.println("查到粉丝数" + fensi.size());
		System.out.println(fensi.size());
		for (User u : fensi) {
			u.setFollowingsCount(3);
			u.setFollowedCount(3);
			u.setPostCount(1);
		}
		session.put("following", this.fensi);
		return "success";
	}

	public List<User> getFensi() {
		return fensi;
	}

	public void setFensi(List<User> fensi) {
		this.fensi = fensi;
	}

	public String googlePosition() {
		// 转换经纬度为位置的函数
		String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
				+ this.latitude + "," + this.longitude + "&sensor=true";
		System.out.println(url);
		// url="http://ditu.google.com/maps/geo?output=csv&key=abcdef&q="+latitude+","+longitude;
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		//
		try {
			httpsConn = (URLConnection) myURL.openConnection();
			InputStreamReader insr = new InputStreamReader(
					httpsConn.getInputStream(), "UTF-8");
			BufferedReader br = new BufferedReader(insr);
			String data = new String();
			String bufferString = br.readLine();

			while (bufferString != null) {
				data += bufferString;
				data += "\n";
				bufferString = br.readLine();
			}
			position = new String(data);
			System.out.println(position);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String getPosition() {
		return position;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	private void InitAction() {
		webRootPath = ServletActionContext.getServletContext().getRealPath("/");
		Resource res = new FileSystemResource(webRootPath
				+ "WEB-INF\\applicationContext.xml");
		XmlBeanFactory factory = new XmlBeanFactory(res);
		m_service = (MessageService) factory.getBean("messageService");
		u_service = (UserService) factory.getBean("userService");
		ActionContext ctx = ActionContext.getContext();
		session = ctx.getSession();
		current_usr_from_session = (User) session.get("user");
	}


	
	
	public String replaceFace(String text)
	{
		 String[] faces={"smile","naughty","cry","angry","embarrass","crazy","effort","despise","lovely","laugh","titter","surprise","orz","unhappy","wronged"};

	    for(String face:faces)
	     {
	    	String fullface="["+face+"]";
	    	String imgsrc="<img src=\"Face/"+face+".gif\"/>";
	    	text=text.replace(fullface, imgsrc);
	     }//for
	    return text;
	}
//	private void afterAction() {
//		for (Message m : myMessages) {
//			String timeDescription = org.atmars.service.TimeServiceImpl
//					.getTimeDelay(m.getTime());
//			m.setTimeDescription(timeDescription);
//			//String o_text = m.getText();
//			//String after_text = replaceFace(o_text);
//			//m.setText(after_text);
//		}
//
//		Iterator iter = not_original.entrySet().iterator();
//		while (iter.hasNext()) {
//			Map.Entry<String, Message> entry = (Map.Entry) iter.next();
//			String timeDescription = org.atmars.service.TimeServiceImpl
//					.getTimeDelay(((Message) entry.getValue()).getTime());
//			((Message) entry.getValue()).setTimeDescription(timeDescription);
//			//String o_text = ((Message) entry.getValue()).getText();
//			//String after_text = replaceFace(o_text);
//			//((Message) entry.getValue()).setText(after_text);
//		}
//		
//		for (Message m : new_Messages) {
//			String timeDescription = org.atmars.service.TimeServiceImpl
//					.getTimeDelay(m.getTime());
//			m.setTimeDescription(timeDescription);
//			//String o_text = m.getText();
//			//String after_text = replaceFace(o_text);
//			//m.setText(after_text);
//		}
//		if (lastPost != null) {
//			String timeDescription = org.atmars.service.TimeServiceImpl
//					.getTimeDelay(lastPost.getTime());
//			lastPost.setTimeDescription(timeDescription);
//			
//		}
//		if (newestMessage_Now != null) {
//			String timeDescription = org.atmars.service.TimeServiceImpl
//					.getTimeDelay(newestMessage_Now.getTime());
//			newestMessage_Now.setTimeDescription(timeDescription);
//		}
//	}

	private boolean ImageUpload() throws IOException {
		System.out.println("the image is "+this.upload);
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
		upload2 = this.upload.substring(index1+1);
		index1  = upload2.indexOf('/');
		String upload3 = upload2.substring(index1) ;
		byte[] b= decoder.decodeBuffer(upload3);
	
		FileOutputStream fos = new FileOutputStream(fn,true);
         fos.write(b);   
         fos.flush();     
		imageURI = "image/"
				+ String.valueOf(current_usr_from_session.getUserId()) + "/"
				+ filename;
		return true;
	}

	public String init_weiboQiang() {
		InitAction();
		qiangCount = 0;
		List l = m_service.findNewestMessageS();
		new_Messages = new ArrayList<Message>();
		System.out.println(l.size());
		while (l != null && qiangCount < l.size()) {
			Message m = (Message) l.get(qiangCount);
			m.getUser().setPassword(null);
			m.getUser().setMessages(null);
			String timeDescription = TimeServiceImpl.getTimeDelay(m.getTime());
			m.setTimeDescription(timeDescription);
			String text2 = m.getText();
			m.setText(text2);
			
			new_Messages.add(m);
			qiangCount++;
		}
		System.out.println(new_Messages.size());
		return "success";
	}

	public String getNewestMessage() {

		InitAction();

		if (currentNewestMessageId == -1) {
			return "error";
		}

		List l = m_service.findNewestMessageS();
		if (l == null || l.size() == 0) {
			return "false";
		}

		Message m = (Message) l.get(0);
		if (m.getMessageId() > currentNewestMessageId) {
			newestMessage_Now = m;
			System.out.println(m.getUser().getImage());
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
			newestMessage_Now.setText(text2);
			return "success";
		}
		return "error";
	}

	public String execute_getMessages() throws Exception {
		InitAction();
		if (current_usr_from_session == null) {
			return "error";
		}
		List l = null;
		System.out.println(oldest_message_id);
		System.out.println("user id"
				+ current_usr_from_session.getUserId().intValue());
		if (oldest_message_id == -1) {
			l = m_service.getMessage(current_usr_from_session.getUserId()
					.intValue());
		} else {
			l = m_service.getMessage(current_usr_from_session.getUserId()
					.intValue(), oldest_message_id);
		}
		// l = m_service.getMessage(3);
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
			m.setUser(u2);
			m.setText((String) properties[2]);
			m.setImage((String) properties[3]);
			m.setTime((Date) properties[4]);
			m.setPosition((String) properties[5]);
			m.setSourceId((Integer) properties[6]);
			m.setCommentCount((Integer) properties[7]);
			System.out.println(m.getText());
			String timeDescription = org.atmars.service.TimeServiceImpl
					.getTimeDelay(m.getTime());
			m.setTimeDescription(timeDescription);
			String text2 = m.getText();
			String text3 = replaceFace(text2);
			m.setText(text3);
			myMessages.add(m);
			i++;
		}
		System.out.println(myMessages.size());
		not_original = new HashMap<String, Message>();
		for (Message m : myMessages) {
			if (m.getSourceId() != -1) {
				System.out.println(m.getSourceId());
				Message m2 = m_service.getSingleMessage(m.getSourceId());
				if (m2 != null) {
					m2.getUser().setComments(null);
					m2.getUser().setFavorites(null);
					m2.getUser().setFollowsForFollowedId(null);
					m2.getUser().setFollowsForFollowingId(null);
					m2.getUser().setMessages(null);
					not_original.put(String.valueOf(m2.getMessageId()), m2);
				} else {
					// not_original.put(key, value)
				}
			}
		}
		return "success";
	}

	public List<Message> getMyMessages() {
		return myMessages;
	}

	public void setMyMessages(List<Message> myMessages) {
		this.myMessages = myMessages;
	}

	public List<Message> getNew_Messages() {
		return new_Messages;
	}

	public void setNew_Messages(List<Message> new_Messages) {
		this.new_Messages = new_Messages;
	}

	public int getQiangCount() {
		return qiangCount;
	}

	public void setQiangCount(int qiangCount) {
		this.qiangCount = qiangCount;
	}

	public int getOldest_message_id() {
		return oldest_message_id;
	}

	public void setOldest_message_id(int oldest_message_id) {
		this.oldest_message_id = oldest_message_id;
	}

	public String execute_sendMessage() throws Exception {

		InitAction();
		//byte[] b = this.text.getBytes("ISO-8859-1");
		//String text2 = new String(b, "utf-8");
		imageURI=null;
		
		
		
		
		
		
		ImageUpload();
		if (imageURI!=null) {
			lastPost = m_service.sendMessage(
					current_usr_from_session.getUserId(), text, imageURI,
					position, source_id);
		} else {
			lastPost = m_service.sendMessage(
					current_usr_from_session.getUserId(), text, null,
					position, source_id);
			
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
		String text2 = replaceFace(lastPost.getText());
		lastPost.setText(text2);
		return "success";
	}

	public void execute_sendComment() throws Exception {
		InitAction();
		// MessageService messageService=new
		// Set<Comment> comments=m_service.getComment(messageid);
		String text = null;
		m_service.sendComment(current_usr_from_session.getUserId(), messageid,
				text);
	}

	// 转发功能

	public String execute_zhuanfa() throws Exception {

		InitAction();

		Message m = m_service.getMessageDAO().findById(messageid);

		if (m == null) {
			System.out.println("return error");
			return "error";
		}
		int sour_id = m.getSourceId();
		if (sour_id == -1) {
			//byte[] b = this.text.getBytes("ISO-8859-1");
			//String textutf8 = new String(b, "utf-8");
			m_service.sendMessage(current_usr_from_session.getUserId(),
					text, null, position, messageid);
			return "success";
		}
		//byte[] b = this.text.getBytes("ISO-8859-1");
		//String textutf8 = new String(b, "utf-8");
		String text2 = text + "from:" + m.getUser().getNickname() + ":"
				+ m.getText();
		m_service.sendMessage(current_usr_from_session.getUserId(), text2,
				null, position, sour_id);

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
		String text3 = replaceFace(lastPost.getText());
		lastPost.setText(text3);
		
		
		
		
		return "success";
	}

	// 收藏功能

	public void execute_favorate() throws Exception {
		InitAction();
		m_service.addFavoriteMessage(current_usr_from_session.getUserId(),
				messageid);

	}

	public void execute_hot() throws Exception {
		InitAction();
		hotTopics = m_service.getHotTopic();
		
	}

	public String getWebRootPath() {
		return webRootPath;
	}

	public void setWebRootPath(String webRootPath) {
		this.webRootPath = webRootPath;
	}

	public MessageService getM_service() {
		return m_service;
	}

	public void setM_service(MessageService m_service) {
		this.m_service = m_service;
	}

	public UserService getU_service() {
		return u_service;
	}

	public void setU_service(UserService u_service) {
		this.u_service = u_service;
	}

	public int getCurrentNewestMessageId() {
		return currentNewestMessageId;
	}

	public void setCurrentNewestMessageId(int currentNewestMessageId) {
		this.currentNewestMessageId = currentNewestMessageId;
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

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public Message getNewestMessage_Now() {
		return newestMessage_Now;
	}

	public void setNewestMessage_Now(Message newestMessage_Now) {
		this.newestMessage_Now = newestMessage_Now;
	}

	public User getCurrent_usr_from_session() {
		return current_usr_from_session;
	}

	public void setCurrent_usr_from_session(User current_usr_from_session) {
		this.current_usr_from_session = current_usr_from_session;
	}

	public Integer getMessageid() {
		return messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid = messageid;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public List<Topic> getHotTopics() {
		return hotTopics;
	}

	public void setHotTopics(List<Topic> hotTopics) {
		this.hotTopics = hotTopics;
	}

	public Map<String, Message> getNot_original() {
		return not_original;
	}

	public void setNot_original(Map<String, Message> not_original) {
		this.not_original = not_original;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public int getSource_id() {
		return source_id;
	}

	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message getLastPost() {
		return lastPost;
	}

	public void setLastPost(Message lastPost) {
		this.lastPost = lastPost;
	}

	// 评论功能
}
