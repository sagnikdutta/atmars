package org.atmars.service.interfaces;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.atmars.dao.User;

public interface UserService {
	public void register(String email, String password, String nickname, boolean gender);
	public boolean checkLogin(String email, String password);
	public String CalculateTicket(User user) throws UnsupportedEncodingException;
	public boolean AuthenticUserWithTicket(int userId,String ticket);
	public boolean isAdministrator(String username);
	public boolean addFollowing(Integer myid, Integer hisid);
	public boolean alreadyFollowing(int me,int him);
	public void updateUserInfo(User instance);
	public User getUserInfoByUserId(Integer userId);
	public User getUserInfoByEmail(String email);
	public User getUserInfoByNickname(String nickname);
	public void deleteUser(User user);
	public List<User> GetNewRegisterUsers();
	public List<User> SearchUser(String searchString,Integer currentUserId);
	public List<User> GetFollowings(int userId);
	public List<User> GetFollowers(int userId);
}
