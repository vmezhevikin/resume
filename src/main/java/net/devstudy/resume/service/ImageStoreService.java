package net.devstudy.resume.service;

import java.io.File;

import javax.annotation.Nonnull;

import net.devstudy.resume.Constants.ImageType;

public interface ImageStoreService {
	
	@Nonnull String saveImageAndReturnLink(@Nonnull File image, String name, ImageType imageType);
	
	void removeImage(@Nonnull String imagePath);
}