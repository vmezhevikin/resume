package net.devstudy.resume.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.devstudy.resume.entity.Profile;

public interface FindProfileService
{
	Profile findByUid(String uid);
	
	Profile findById(long id);
	
	Profile findByEmail(String email);

	Page<Profile> findAll(Pageable pageable);
	
	Iterable<Profile> findAllForIndexing();
	
	Page<Profile> findBySearchQuery(String query, Pageable pageable);
	
	Profile findByUniqueId(String anyUniqueId);
	
	Profile findByToken(String token);
}