package org.atmars.service.interfaces;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.atmars.dao.FollowDAO;
import org.atmars.dao.User;
import org.atmars.dao.UserDAO;

public interface UserService {
	
	public void register(String email, String password, String nickname, boolean gender);
	public boolean checkLogin(String username, String password);
	public boolean isAdministrator(String username);
	public void addFollowing(Integer myid, Integer hisid);
	public User getUserInfo(Integer id);
	
	public Integer getId(String username);
	public void removeFollowing(Integer id);
	public void updateUserInfo(User instance);
	public String getHashedPassword(String password);
	public List getUserInfoByEmail(String email);
	public UserDAO getUserDAO();
	public FollowDAO getFollowDAO();
	
	public List<User> GetNewRegisterUsers();
}
