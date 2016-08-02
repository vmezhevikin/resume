package net.devstudy.resume.repository.storage;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Profile;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {
	
	List<Profile> findAll(Sort sort);

	Profile findById(Long id);

	Profile findByUid(String uid);

	Profile findByEmail(String email);

	Profile findByPhone(String phone);

	Profile findByUidOrEmailOrPhone(String uid, String email, String phone);

	int countByUid(String uid);

	int countByEmail(String email);

	int countByPhone(String phone);

	List<Profile> findByActiveFalseAndCreatedBefore(Timestamp date);

	Profile findByProfileRestoreToken(String token);
	
	Page<Profile> findAllByActiveTrue(Pageable pageable);
}
