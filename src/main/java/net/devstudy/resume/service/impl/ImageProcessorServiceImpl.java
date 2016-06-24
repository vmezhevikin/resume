package net.devstudy.resume.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.devstudy.resume.Constants.ImageType;
import net.devstudy.resume.component.ImageResizer;
import net.devstudy.resume.exception.ImageStoreServiceException;
import net.devstudy.resume.model.UploadImageResult;
import net.devstudy.resume.service.ImageProcessorService;
import net.devstudy.resume.service.ImageStoreService;
import net.devstudy.resume.util.DataUtil;

@Service
public class ImageProcessorServiceImpl implements ImageProcessorService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessorServiceImpl.class);

	@Autowired
	private ImageStoreService imageStoreService;

	@Autowired
	private ImageResizer imageResizer;

	@Value("${temp.files.folder}")
	private String tempFilesFolder;

	@Override
	public UploadImageResult processProfilePhoto(MultipartFile newPhoto) {
		LOGGER.info("Processing new profile photo");
		return optimizeAndSaveImages(newPhoto, ImageType.AVATAR);
	}

	@Override
	public UploadImageResult processProfileCertificate(MultipartFile newCertificate) {
		LOGGER.info("Processing new profile certificate");
		return optimizeAndSaveImages(newCertificate, ImageType.CERTIFICATE);
	}

	private UploadImageResult optimizeAndSaveImages(MultipartFile uploadFile, ImageType imageType) {
		String tempFileName = DataUtil.generateImageUid() + ".jpg";
		String largeImageName = DataUtil.generateImageUid() + ".jpg";
		String smallImageName = largeImageName.replace(".jpg", "-sm.jpg");

		File tempFile = null;
		File largeImageTempFile = null;
		File smallImageTempFile = null;

		try {
			tempFile = new File(tempFilesFolder + tempFileName);
			largeImageTempFile = new File(tempFilesFolder + largeImageName);
			smallImageTempFile = new File(tempFilesFolder + smallImageName);

			uploadFile.transferTo(tempFile);

			imageResizer.resize(tempFile, largeImageTempFile, imageType.getLargeWidth(), imageType.getLargeHeight());
			imageResizer.resize(tempFile, smallImageTempFile, imageType.getSmallWidth(), imageType.getSmallHeight());
			
			String largeImageUrl = imageStoreService.saveImageAndReturnLink(largeImageTempFile, largeImageName, imageType);
			String smallImageUlr = imageStoreService.saveImageAndReturnLink(smallImageTempFile, smallImageName, imageType);

			return new UploadImageResult(largeImageUrl, smallImageUlr);
		} catch (IOException cause) {
			LOGGER.error("Can't store new user image");
			throw new ImageStoreServiceException("Can't store new user image", cause);
		} finally {
			if (largeImageTempFile != null) {
				removeTempFile(largeImageTempFile);
			}
			if (smallImageTempFile != null) {
				removeTempFile(smallImageTempFile);
			}
			if (tempFile != null) {
				removeTempFile(tempFile);
			}
		}
	}

	@Override
	public void removeProfilePhoto(String imagePath) {
		LOGGER.info("Removing profile photo" + imagePath);
		imageStoreService.removeImage(imagePath);
	}

	@Override
	public void removeProfileCertificate(String imagePath) {
		LOGGER.info("Removing profile certificate" + imagePath);
		imageStoreService.removeImage(imagePath);
	}

	private void removeTempFile(File removeFile) {
		LOGGER.debug("Removing temporary file {}", removeFile.getName());
		String removeFilePath = removeFile.getAbsolutePath();
		try {
			Path destFilePath = Paths.get(removeFilePath);
			Files.deleteIfExists(destFilePath);
			LOGGER.debug("File {} has been removed", removeFilePath);
		} catch (IOException cause) {
			LOGGER.warn("Can't remove file " + removeFilePath, cause);
		}
	}
}
