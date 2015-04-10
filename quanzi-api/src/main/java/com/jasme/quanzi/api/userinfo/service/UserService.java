package com.jasme.quanzi.api.userinfo.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasme.quanzi.api.userinfo.exception.UserBlockedException;
import com.jasme.quanzi.api.userinfo.exception.UserNotExistsException;
import com.jasme.quanzi.api.userinfo.exception.UsernameExistsException;
import com.jasme.quanzi.api.userinfo.repository.UserRepository;
import com.jasme.quanzi.api.userinfo.request.view.RegistView;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.quanzi.core.component.user.enums.UserStatus;
import com.jasme.swiiket.common.service.BaseService;

@Service
public class UserService extends BaseService<User, Long> {

	private UserRepository userRepository;
	private PasswordService passwordService;

	@Autowired
	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User regist(RegistView view) {
		// TODO Auto-generated method stub
		// 检查账号名是否已被注册
		if (userRepository.checkUsername(view.getUsername()) > 0) {

			throw new UsernameExistsException();
		}

		User user = new User();
		user.setUsername(view.getUsername());
		user.randomSalt();
		String password = passwordService.encryptPassword(view.getPassword(), user.getSalt());
		user.setPassword(password);
		user.setRegistDate(new Date());

		return super.save(user);
	}

	public User login(String username, String password, boolean autoLogin) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {

			ERROR_LOG.error("Login Error, username or password is empty.");
			throw new UserNotExistsException();
		}
		
		User user = userRepository.findByUsername(username, Boolean.FALSE);
		
		if (null == user || Boolean.TRUE == user.getDeleted()) {
			ERROR_LOG.error("Login Error, user not exist");
			throw new UserNotExistsException();
		}
		// 密码校验
		passwordService.validate(user, password, autoLogin);
		
		if (UserStatus.BLOCKED == user.getStatus()) {
			ERROR_LOG.debug("[" + username + "] is blocked!");
			throw new UserBlockedException();
		}
		
		INFO_LOG.info("[" + username + "] login success!");
		
		return user;
	}

}
