package com.inspur.service.impl;

import com.inspur.entity.User;
import com.inspur.mapper.UserMapper;
import com.inspur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: YANG
 * Date: 2019/8/5-12:32
 * Description: No Description
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User queryUserByUserId(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int deleteUserByUserId(Long id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<User> queryUserList() {
		return userMapper.selectAll();
	}
}
