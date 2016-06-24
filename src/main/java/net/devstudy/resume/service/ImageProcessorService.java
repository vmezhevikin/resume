package net.devstudy.resume.service;

import javax.annotation.Nonnull;

import org.springframework.web.multipart.MultipartFile;

import net.devstudy.resume.model.UploadImageResult;

public interface ImageProcessorService {
	
	@Nonnull UploadImageResult processProfilePhoto(@Nonnull MultipartFile newPhoto);

	@Nonnull UploadImageResult processProfileCertificate(@Nonnull MultipartFile newCertificate);
	
	void removeProfilePhoto(@Nonnull String imagePath);
	
	void removeProfileCertificate(@Nonnull String imagePath);
}