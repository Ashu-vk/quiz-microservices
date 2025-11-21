package com.user.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.user.model.UserLoginAudit;

public interface UserLoginAuditRepo extends CrudRepository<UserLoginAudit, UUID> {
	List<UserLoginAudit> findByUserIdAndRefreshTokenActiveTrue(Long accessToken);

	List<UserLoginAudit> findByRefreshTokenAndRefreshTokenActiveTrue(String refreshToken);
}
