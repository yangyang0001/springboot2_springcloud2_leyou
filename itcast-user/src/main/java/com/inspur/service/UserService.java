package com.inspur.service;

import com.inspur.entity.User;

import java.util.List;

/**
 * User: YANG
 * Date: 2019/8/5-12:31
 * Description: No Description
 */
public interface UserService {

	public User queryUserByUserId(Long id);

	public int deleteUserByUserId(Long id);

	public List<User> queryUserList();
}
