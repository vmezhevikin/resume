package net.devstudy.resume.repository.storage;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Profile;

//@RepositoryDefinition(domainClass=Profile.class, idClass=Long.class)
//public interface ProfileRepository extends JpaRepository<Profile, Long>
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long>
{
	List<Profile> findAll(Sort sort);
	
	Profile findById(Long id);
	
	Profile findByUid(String uid);
	
	Profile findByEmail(String email);
	
	Profile findByPhone(String phone);
	
	int countByUid(String uid);
	
	List<Profile> findByActiveFalseAndCreatedBefore(Timestamp date);
	
	Profile findByProfileRestoreToken(String token);
}
