package org.apache.shiro.web.session.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbaseSessionListener implements SessionListener {

	public static final Logger LOG = LoggerFactory.getLogger(DbaseSessionListener.class);
	
	@Override
	public void onStart(Session session) {
		// TODO Auto-generated method stub
		LOG.info("会话创建：" + session.getId());
	}

	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub
		LOG.info("会话停止：" + session.getId());
	}

	@Override
	public void onExpiration(Session session) {
		// TODO Auto-generated method stub
		LOG.info("会话过期：" + session.getId());
	}

}
