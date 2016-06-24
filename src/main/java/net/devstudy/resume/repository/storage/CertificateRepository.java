package net.devstudy.resume.repository.storage;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Certificate;

public interface CertificateRepository extends PagingAndSortingRepository<Certificate, Long> {
	
	List<Certificate> findByProfileId(long idProfile);
}
