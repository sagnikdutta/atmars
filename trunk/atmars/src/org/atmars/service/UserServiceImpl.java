package org.atmars.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.atmars.dao.Follow;
import org.atmars.dao.FollowDAO;
import org.atmars.dao.User;
import org.atmars.dao.UserDAO;
import org.atmars.service.interfaces.UserService;
import org.jasypt.util.password.BasicPasswordEncryptor;

public class UserServiceImpl implements UserService {

	private UserDAO userDAO;
	private FollowDAO followDAO;
	
	
	//@Override
	//public Search()
	//{
		
	//}
	@Override
	public FollowDAO getFollowDAO()
	{
		return this.followDAO;
	}

	public UserServiceImpl(UserDAO userDAO, FollowDAO followDAO){
		this.userDAO = userDAO;
		this.followDAO = followDAO;
	}
	
	@Override
	public UserDAO getUserDAO()
	{
		return this.userDAO;
	}
	
	
	@Override
	public String getHashedPassword(String password)
	{
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		return passwordEncryptor.encryptPassword(password);
	}
	
	@Override
	public void updateUserInfo(User instance)
	{
		userDAO.attachDirty(instance);
	}
	
	
	@Override 
	public List getUserInfoByEmail(String email)
	{
		return userDAO.findByProperty("email",email );
	}
 

	@Override
	public void register(String email, String password, String nickname,
			boolean gender) {
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(password);
		User user = new User();
		user.setEmail(email);
		user.setPassword(encryptedPassword);
		user.setNickname(nickname);
		user.setGender(gender);
		user.setFollowerCount(0);
		user.setFollowingCount(0);
		user.setPostCount(0);

		userDAO.save(user);
		User u = (User) userDAO.findByExample(user).get(0);
		Follow f = new Follow();
		f.setUserByFollowingId(user);
		f.setUserByFollowedId(user);
		followDAO.save(f);
	}

	@Override
	public boolean checkLogin(String username, String password) {
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		List l = userDAO.findByEmail(username);
		if(l==null||l.size()==0)
		{
			return false;
		}
		User user = (User)userDAO.findByEmail(username).get(0);
		if(passwordEncryptor.checkPassword(password, user.getPassword())){
			return true;
		}
		return false;
	}

	@Override
	public boolean isAdministrator(String username) {
		User user = (User)userDAO.findByEmail(username).get(0);
		if (user.getPriority() == 1){
			return true;
		}
		return false;
	}

	@Override
	public void addFollowing(Integer myid, Integer hisid) {
		User me = userDAO.findById(myid);
		User him = userDAO.findById(hisid);
		Follow follow = new Follow();
		follow.setUserByFollowingId(me);
		follow.setUserByFollowedId(him);
		followDAO.save(follow);
	}

	@Override
	public User getUserInfo(Integer id) {
		User user = userDAO.findById(id);
		return user;
	}

	@Override
	public Integer getFollowingNumber(Integer id) {
		User user = userDAO.findById(id);
		return user.getFollowsForFollowedId().size();
	}

	@Override
	public Integer getFollowedNumber(Integer id) {
		User user = userDAO.findById(id);
		return user.getFollowsForFollowingId().size();
	}

	@Override
	public Integer getPostsNumber(Integer id) {
		User user = userDAO.findById(id);
		return user.getMessages().size();
	}

	@Override
	public Integer getId(String email) {
		System.out.println(email);
		List user = userDAO.findByEmail(email);
		if(user==null || user.size()==0)
		{
			return null;
		}
		return ((User)user.get(0)).getUserId();
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	@Override
	public void removeFollowing(Integer id) {
		followDAO.delete(followDAO.findById(id));
	}
}
