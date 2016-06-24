package net.devstudy.resume.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.devstudy.resume.Constants.ImageType;
import net.devstudy.resume.exception.ImageStoreServiceException;
import net.devstudy.resume.service.ImageStoreService;

@Service
public class FileImageStoreService implements ImageStoreService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileImageStoreService.class);

	@Value("${media.folder}")
	private String mediaFolder;

	@Value("${avatar.folder}")
	private String avatarFolder;

	@Value("${certificate.folder}")
	private String certificateFolder;

	@Override
	public String saveImageAndReturnLink(File imageFile, String imageName, ImageType imageType) {
		LOGGER.debug("Saving new image {} to file storage", imageName);
		String imageURL = null;
		File destImageFile = null;
		try {
			if (imageType == ImageType.AVATAR) {
				imageURL = mediaFolder + avatarFolder + imageName;
			}
			if (imageType == ImageType.CERTIFICATE) {
				imageURL = mediaFolder + certificateFolder + imageName;
			}
			destImageFile = new File(imageURL);
			Files.copy(imageFile.toPath(), destImageFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
		} catch (IOException cause) {
			LOGGER.error("Can't copy source file " + imageName);
			if (destImageFile != null) {
				removeDestinationFileIfException(destImageFile);
			}
			throw new ImageStoreServiceException("Can't copy source file " + imageName, cause);
		}
		return imageURL.replace(mediaFolder, "");
	}

	private void removeDestinationFileIfException(File removeFile) {
		LOGGER.debug("Removing destination file {} after exception", removeFile.getName());
		String removeFilePath = removeFile.getAbsolutePath();
		try {
			Path destFilePath = Paths.get(removeFilePath);
			Files.deleteIfExists(destFilePath);
			LOGGER.debug("File {} has been removed", removeFilePath);
		} catch (IOException cause) {
			LOGGER.warn("Can't remove file " + removeFilePath, cause);
		}
	}

	@Override
	public void removeImage(String imagePath) {
		LOGGER.debug("Removing image {} from file storage", imagePath);
		try {
			Path destFilePath = Paths.get(mediaFolder + imagePath);
			Files.deleteIfExists(destFilePath);
			LOGGER.debug("File {} has been removed", imagePath);
		} catch (IOException cause) {
			LOGGER.warn("Can't remove file " + imagePath, cause);
		}
	}
}
