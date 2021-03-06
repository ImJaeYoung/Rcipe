package com.rcipe.service.user.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.rcipe.service.domain.User;
import com.rcipe.service.user.UserDAO;

@Repository("userDAOImpl")
public class UserDAOImpl  implements UserDAO{

	 @Autowired
	 @Qualifier("sqlSessionTemplate")
	 private  SqlSession sqlSession;
	 
	 public UserDAOImpl() {
			System.out.println("UserDAOImpl default Constractor......");
		}
	 public UserDAOImpl(SqlSession sqlSession) {
		 this.sqlSession=sqlSession;
			System.out.println("UserDAOImpl sqlSession Constractor......");
		}
	 public SqlSession getSqlSession() {
			return sqlSession;
		}
		public void setSqlSession(SqlSession sqlSession) {
			this.sqlSession = sqlSession;
		}
	@Override
	public int insertUser(User user) throws Exception{
		return sqlSession.insert("UserMapper.insertUser",user);
	}

	@Override
	public String checkedEmail(String email) throws Exception{
		return sqlSession.selectOne("UserMapper.checkedEmail",email);
	}

	@Override
	public String checkedNickname(String nickname) throws Exception{
		return sqlSession.selectOne("UserMapper.checkedNickname",nickname);
	}

	@Override
	public User checkedLogin(User user)throws Exception {
		return sqlSession.selectOne("UserMapper.checkedLogin", user);
	}
	
	public User getUser(String email) throws Exception {
		 User user =sqlSession.selectOne("UserMapper.getUser", email);
		 System.err.println(user);
		return user;
	}
	
	@Override
	public int updatePassword(User user) throws Exception {
		return sqlSession.update("UserMapper.updatePassword", user);
	}

	@Override
	public int updateImage(User user) throws Exception {
		System.out.println(user);
		return sqlSession.update("UserMapper.updateImage", user);
	}
	
	@Override
	public String getUserImage(String nickname) throws Exception {
		return sqlSession.selectOne("UserMapper.getUserImage", nickname);
	}

	@Override
	public int deleteUser(String nickname) throws Exception {
		return sqlSession.update("UserMapper.deleteUser", nickname);
	}
	

}
