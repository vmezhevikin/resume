package net.devstudy.resume.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.devstudy.resume.entity.Profile;

public interface FindProfileService {
	
	@Nullable Profile findByUid(@Nonnull String uid);
	
	@Nullable Profile findById(long id);
	
	@Nullable Profile findByEmail(@Nonnull String email);

	@Nonnull Page<Profile> findAll(@Nonnull Pageable pageable);
	
	@Nonnull Iterable<Profile> findAllForIndexing();
	
	@Nonnull Page<Profile> findBySearchQuery(@Nonnull String query, @Nonnull Pageable pageable);
	
	@Nullable Profile findByUniqueId(@Nonnull String anyUniqueId);
	
	@Nullable Profile findByToken(@Nonnull String token);

	@Nonnull List<Profile> findNotCompletedProfilesCreatedBefore(@Nonnull Timestamp date);
}