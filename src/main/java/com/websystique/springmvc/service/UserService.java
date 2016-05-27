package com.websystique.springmvc.service;

import com.websystique.springmvc.model.TblUser;

import java.util.List;



public interface UserService {

	TblUser findById(long id);

	TblUser findByName(String name);

	void saveUser(TblUser user);

	void updateUser(TblUser user);

	void deleteUserById(long id);

	List<TblUser> findAllUsers();

	void deleteAllUsers();

	boolean isUserExist(TblUser user);

}
