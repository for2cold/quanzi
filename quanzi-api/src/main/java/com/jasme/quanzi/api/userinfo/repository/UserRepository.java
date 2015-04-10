package com.jasme.quanzi.api.userinfo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.swiiket.common.repository.BaseRepository;

public interface UserRepository extends BaseRepository<User, Long> {

	@Query("select count(u.id) from User u where u.username = :username")
	long checkUsername(@Param("username") String username);

	@Query("from User u where u.username = :username and u.admin = :admin")
	User findByUsername(@Param("username") String username, @Param("admin") Boolean admin);

}
