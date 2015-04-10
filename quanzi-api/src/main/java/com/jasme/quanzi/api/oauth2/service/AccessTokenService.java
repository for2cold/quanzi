package com.jasme.quanzi.api.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasme.quanzi.api.oauth2.repository.AccessTokenRepository;
import com.jasme.quanzi.core.oauth2.entity.AccessToken;
import com.jasme.swiiket.common.service.BaseService;

@Service
public class AccessTokenService extends BaseService<AccessToken, String> {

	private AccessTokenRepository accessTokenRepository;

	@Autowired
	public void setAccessTokenRepository(AccessTokenRepository accessTokenRepository) {
		this.accessTokenRepository = accessTokenRepository;
	}
	
}
