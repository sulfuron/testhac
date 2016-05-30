package com.websystique.springmvc.service.impl;

import com.websystique.springmvc.dao.TblUserMapper;
import com.websystique.springmvc.model.TblUser;
import com.websystique.springmvc.model.TblUserExample;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Transactional
public class UserServiceImpl implements UserService {


	private static final AtomicLong counter = new AtomicLong();

	private static List<TblUser> users;

	private static org.apache.commons.pool.impl.GenericObjectPool gp;

	@Autowired
	private TblUserMapper userMapper;

	public List<TblUser> findAllUsers() {
		TblUserExample e = new TblUserExample();
		//创建查询条件
		e.createCriteria().andEmailIsNotNull();
		return userMapper.selectByExample(e);
	}

	public TblUser findById(long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public TblUser findByName(String name) {
		for (TblUser user : users) {
			if (user.getLoginName().equalsIgnoreCase(name)) {
				return user;
			}
		}
		return null;
	}

	public void saveUser(TblUser user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(TblUser user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {

		for (Iterator<TblUser> iterator = users.iterator(); iterator.hasNext(); ) {
			TblUser user = iterator.next();
			if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(TblUser user) {
		return findByName(user.getLoginName()) != null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

}
