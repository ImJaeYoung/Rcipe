package com.rcipe.service.user;

import com.rcipe.service.domain.User;

public interface UserDAO {
	public int insertUser(User user) throws Exception;

	public String checkedEmail(String email) throws Exception;

	public String checkedNickname(String nickname) throws Exception;

	public User checkedLogin(User user)throws Exception ;

	public User getUser(String email) throws Exception;

	public int updatePassword(User user) throws Exception;

	public int updateImage(User user) throws Exception;

	public int deleteUser(String nickname) throws Exception;
	
	public String getUserImage(String nickname) throws Exception;
}
